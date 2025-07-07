package io.github.joaocastro20.estoquenexdom.service;

import io.github.joaocastro20.estoquenexdom.domain.Produto;
import io.github.joaocastro20.estoquenexdom.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final ProdutoRepository produtoRepository;

    public byte[] gerarRelatorio(Map<String, Object> parametros, List<?> dados, String nomeTemplate) throws JRException {
        InputStream inputStream = getClass().getResourceAsStream("/reports/" + nomeTemplate + ".jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dados);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    public byte[] relatorioTotal(Map<String, Object> parametros, String nomeTemplate) throws JRException {
        List<Produto> listaProdutos = produtoRepository.findAll();
        if(listaProdutos.isEmpty()){
            throw new IllegalArgumentException("Não Há Produtos Cadastrados!");
        }
        return gerarRelatorio(parametros, listaProdutos, nomeTemplate);
    }
}
