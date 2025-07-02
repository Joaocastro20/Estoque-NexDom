package io.github.joaocastro20.estoquenexdom.service;

import io.github.joaocastro20.estoquenexdom.domain.MovimentoEstoque;
import io.github.joaocastro20.estoquenexdom.domain.Produto;
import io.github.joaocastro20.estoquenexdom.domain.enums.TipoProduto;
import io.github.joaocastro20.estoquenexdom.repository.ProdutoRepository;
import net.sf.jasperreports.engine.JRException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class RelatorioServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private RelatorioService relatorioService;


    public RelatorioServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveGerarRelatorioComSucesso() throws JRException {
        String nomeTemplate = "stockV2";

        List<MovimentoEstoque> movimentoEstoqueList = new ArrayList<>();
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO", "Relatório de Produtos");

        List<Produto> dados = List.of(new Produto(1L,"ABC123", "Produto Teste", TipoProduto.MOVEL, new BigDecimal("10.00"), 10, movimentoEstoqueList));

        byte[] pdfBytes = relatorioService.gerarRelatorio(parametros, dados, nomeTemplate);

        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 100);
    }

    @Test
    void deveGerarRelatorioComProdutos() throws Exception {
        Produto produto1 = new Produto();
        produto1.setCodigo("001");
        produto1.setDescricao("Produto A");

        Produto produto2 = new Produto();
        produto2.setCodigo("002");
        produto2.setDescricao("Produto B");

        List<Produto> produtos = List.of(produto1, produto2);
        when(produtoRepository.findAll()).thenReturn(produtos);

        Map<String, Object> parametros = new HashMap<>(Map.of("chave", "valor"));
        parametros.put("outraChave", "outroValor");

        String template = "stockV2";

        byte[] relatorio = relatorioService.relatorioTotal(parametros, template);

        assertNotNull(relatorio);
        assertTrue(relatorio.length > 0);
    }
}
