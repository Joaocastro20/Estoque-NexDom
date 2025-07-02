package io.github.joaocastro20.estoquenexdom.domain.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TipoProdutoTest {

    @Test
    void deveConterTodos() {
        TipoProduto[] tipoProdutos = TipoProduto.values();
        assertArrayEquals(new TipoProduto[]{
                TipoProduto.ELETRONICO,
                TipoProduto.ELETRODOMESTICO,
                TipoProduto.MOVEL
        }, tipoProdutos);
    }

    @Test
    void deveConverterStringParaEnum() {
        assertEquals(TipoProduto.ELETRONICO, TipoProduto.valueOf("ELETRONICO"));
        assertEquals(TipoProduto.ELETRODOMESTICO, TipoProduto.valueOf("ELETRODOMESTICO"));
        assertEquals(TipoProduto.MOVEL, TipoProduto.valueOf("MOVEL"));
    }

    @Test
    void deveLancarExcecaoParaStringInvalida() {
        assertThrows(IllegalArgumentException.class, () -> {
            TipoProduto.valueOf("INVALIDO");
        });
    }
}
