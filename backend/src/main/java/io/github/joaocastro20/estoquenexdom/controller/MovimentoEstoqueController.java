package io.github.joaocastro20.estoquenexdom.controller;

import io.github.joaocastro20.estoquenexdom.domain.MovimentoEstoque;
import io.github.joaocastro20.estoquenexdom.service.MovimentoEstoqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movimentoestoque")
@RequiredArgsConstructor
public class MovimentoEstoqueController {

    private final MovimentoEstoqueService movimentoEstoqueService;

    @PostMapping("/{codigoProduto}")
    public ResponseEntity<MovimentoEstoque> adicionarMovimento(
            @PathVariable String codigoProduto,
            @RequestBody MovimentoEstoque movimento) {

        MovimentoEstoque registrado = movimentoEstoqueService.registrarMovimento(codigoProduto, movimento);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrado);
    }
}
