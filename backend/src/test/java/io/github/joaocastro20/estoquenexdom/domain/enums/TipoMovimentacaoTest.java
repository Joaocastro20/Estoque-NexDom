package io.github.joaocastro20.estoquenexdom.domain.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TipoMovimentacaoTest {

    @Test
    void deveConterEntradaSaida() {
        TipoMovimentacao[] tipoMovimentacaos = TipoMovimentacao.values();
        assertArrayEquals(new TipoMovimentacao[]{
                TipoMovimentacao.ENTRADA,
                TipoMovimentacao.SAIDA
        }, tipoMovimentacaos);
    }

    @Test
    void deveConverterEnumParaString() {
        assertEquals(TipoMovimentacao.ENTRADA, TipoMovimentacao.valueOf("ENTRADA"));
        assertEquals(TipoMovimentacao.SAIDA, TipoMovimentacao.valueOf("SAIDA"));
    }

    @Test
    void deveLancarExcecaoParaStringInvalida() {
        assertThrows(IllegalArgumentException.class, () -> {
            TipoMovimentacao.valueOf("INVALIDO");
        });
    }
}
