package io.github.joaocastro20.estoquenexdom.repository;

import io.github.joaocastro20.estoquenexdom.domain.Produto;
import io.github.joaocastro20.estoquenexdom.domain.enums.TipoProduto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ProdutoRepositoryTest {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    void deveSalvarERecuperarProduto() {
        Produto produto = new Produto();
        produto.setCodigo("PROD001");
        produto.setDescricao("Produto de Teste");
        produto.setTipoProduto(TipoProduto.MOVEL); // Enum existente
        produto.setValorFornecedor(BigDecimal.valueOf(100.50));
        produto.setQuantidadeEstoque(50);
        produto.setMovimentos(new ArrayList<>()); // necessário evitar NullPointer

        Produto salvo = produtoRepository.save(produto);

        Optional<Produto> encontrado = produtoRepository.findById(salvo.getId());

        assertTrue(encontrado.isPresent());
        assertEquals("PROD001", encontrado.get().getCodigo());
        assertEquals(BigDecimal.valueOf(100.50), encontrado.get().getValorFornecedor());
        assertEquals(TipoProduto.MOVEL, encontrado.get().getTipoProduto());
    }
}