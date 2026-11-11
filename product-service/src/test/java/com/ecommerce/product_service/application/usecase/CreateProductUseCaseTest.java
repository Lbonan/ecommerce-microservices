package com.ecommerce.product_service.application.usecase;

import com.ecommerce.product_service.domain.exception.DuplicateSkuException;
import com.ecommerce.product_service.domain.exception.InvalidProductNotException;
import com.ecommerce.product_service.domain.model.Product;
import com.ecommerce.product_service.domain.model.Sku;
import com.ecommerce.product_service.domain.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CreateProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CreateProductUseCase useCase;

    @Test
    void deveCriarESalvarProdutoQuandoSkuNaoExiste() {
        when(productRepository.existsBySku(new Sku("mouse-01"))).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product resultado = useCase.execute(
            "Mouse", "Mouse sem fio", "mouse-01", new BigDecimal("99.90"));

        assertThat(resultado.getName()).isEqualTo("Mouse");
        assertThat(resultado.getSku().value()).isEqualTo("MOUSE-01");
        assertThat(resultado.getPrice()).isEqualByComparingTo("99.90");
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void deveLancarExececaoESalvarNadaQuandoSkuJaExiste() {
        when(productRepository.existsBySku(new Sku("mouse-01"))).thenReturn(true);

        assertThatThrownBy(() -> useCase.execute(
                "Mouse", "Mouse sem fio", "mouse-01", new BigDecimal("99.90")
        )).isInstanceOf(DuplicateSkuException.class);
        verify(productRepository, never()).save(any());
    }

    @Test
    void deveRejeitarSkuInvalidoSemConsultarORepositorio() {
        assertThatThrownBy(() -> useCase.execute(
                "Mouse", null, "   ", new BigDecimal("99.90")
        )).isInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(productRepository);
    }

    @Test
    void naoDeveSalvarQuandoOPrecoEInvalido() {
       assertThatThrownBy(() -> useCase.execute(
               "Mouse", null, "mouse-01", BigDecimal.ZERO
       )).isInstanceOf(InvalidProductNotException.class);
       verify(productRepository, never()).save(any());
    }
}