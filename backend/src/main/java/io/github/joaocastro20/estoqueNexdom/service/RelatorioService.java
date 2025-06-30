package io.github.joaocastro20.estoqueNexdom.service;

import io.github.joaocastro20.estoqueNexdom.domain.Produto;
import io.github.joaocastro20.estoqueNexdom.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final ProdutoRepository produtoRepository;

    public byte[] gerarRelatorio(Map<String, Object> parametros, List<?> dados, String nomeTemplate) throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("/reports/" + nomeTemplate + ".jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dados);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    public byte[] relatorioTotal(Map<String, Object> parametros, String nomeTemplate) throws Exception {
        List<Produto> listaProdutos = produtoRepository.findAll();
        List<Map<String, Object>> produtosMapeados = listaProdutos.stream().map(produto -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", produto.getId());
            map.put("codigo", produto.getCodigo());
            map.put("descricao", produto.getDescricao());
            map.put("tipoProduto", produto.getTipoProduto().toString());
            map.put("valorFornecedor", "R$ " + String.format("%.2f", produto.getValorFornecedor()));
            map.put("quantidadeEstoque", produto.getQuantidadeEstoque());
            return map;
        }).collect(Collectors.toList());
        return gerarRelatorio(parametros, produtosMapeados, nomeTemplate);
    }
}
