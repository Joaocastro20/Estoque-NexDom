package io.github.joaocastro20.estoquenexdom.controller;

import io.github.joaocastro20.estoquenexdom.service.RelatorioService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/relatorio")
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioService relatorioService;

    @GetMapping("/total")
    public ResponseEntity<byte[]> gerarRelatorioTotal() throws JRException {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("titulo", "Relatório Total de Produtos");

        byte[] pdf = relatorioService.relatorioTotal(parametros, "stockV2");

        return ResponseEntity.ok()
                .header("Content-Disposition", "inline; filename=relatorio_produtos.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
