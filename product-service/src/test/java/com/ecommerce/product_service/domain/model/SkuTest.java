package com.ecommerce.product_service.domain.model;

import org.assertj.core.util.Strings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class SkuTest {

    @Test
    void DeveNormalizarParaMaiusculoESemEspacos() {
        assertThat(new Sku(" abc-123 ").value()).isEqualTo("ABC-123");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void deveRejeitarSkuVazioOuNulo(String valor) {
        assertThatThrownBy(() -> new Sku(valor)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void deveRejeitarSkuComMaisDe50Caracteres() {
        assertThatThrownBy(() -> new Sku("A".repeat(51)))
        .isInstanceOf(IllegalArgumentException.class);
    }
}