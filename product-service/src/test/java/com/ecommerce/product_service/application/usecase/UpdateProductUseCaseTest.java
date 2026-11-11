package com.ecommerce.product_service.application.usecase;

import com.ecommerce.product_service.domain.exception.InvalidProductNotException;
import com.ecommerce.product_service.domain.exception.ProductNotFoundException;
import com.ecommerce.product_service.domain.model.Product;
import com.ecommerce.product_service.domain.model.Sku;
import com.ecommerce.product_service.domain.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private UpdateProductUseCase useCase;

    @Test
    void deveAtualizarDadosEPrecoESalvar() {
        Product produto = Product.create(
                "Mouse", null, new Sku("mouse-01"), new BigDecimal("99.90"));
        when(productRepository.findById(produto.getId()))
                .thenReturn(Optional.of(produto));
        when(productRepository.save(produto)).thenReturn(produto);

        Product resultado = useCase.execute(
                produto.getId(), "  Mouse Gamer ", "RGB", new BigDecimal("149.90"));

        assertThat(resultado.getName()).isEqualTo("Mouse Gamer");
        assertThat(resultado.getDescription()).isEqualTo("RGB");
        assertThat(resultado.getPrice()).isEqualByComparingTo("149.90");
        verify(productRepository).save(produto);
    }

    @Test
    void deveLancarExcecaoQuandoOProdutoNaoExiste() {
        UUID id = UUID.randomUUID();
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(
                id, "Mouse", null, new BigDecimal("99.90")))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productRepository, never()).save(any());
    }

    @Test
    void naoDeveSalvarQuandoOPrecoEInvalido() {
        Product produto = Product.create(
                "Mouse", null, new Sku("mouse-01"), new BigDecimal("99.90"));
        when(productRepository.findById(produto.getId()))
                .thenReturn(Optional.of(produto));

        assertThatThrownBy(() -> useCase.execute(
                produto.getId(), "Mouse", null, new BigDecimal("-1")))
                .isInstanceOf(InvalidProductNotException.class);

        verify(productRepository, never()).save(any());
    }
}