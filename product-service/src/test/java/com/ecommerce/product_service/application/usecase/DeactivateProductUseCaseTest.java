package com.ecommerce.product_service.application.usecase;

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
class DeactivateProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private DeactivateProductUseCase useCase;

    @Test
    void deveDesativarOProdutoESalvar() {
        Product produto = Product.create(
                "Mouse", null, new Sku("mouse-01"), new BigDecimal("99.90"));
        when(productRepository.findById(produto.getId()))
                .thenReturn(Optional.of(produto));

        useCase.execute(produto.getId());

        assertThat(produto.isActive()).isFalse();
        verify(productRepository).save(produto);
    }

    @Test
    void deveLancarExcecaoQuandoOProdutoNaoExiste() {
        UUID id = UUID.randomUUID();
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(id))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productRepository, never()).save(any());
    }
}