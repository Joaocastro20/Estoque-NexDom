package io.github.joaocastro20.estoqueNexdom.controller;

import io.github.joaocastro20.estoqueNexdom.domain.MovimentoEstoque;
import io.github.joaocastro20.estoqueNexdom.service.MovimentoEstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movimentoestoque")
public class MovimentoEstoqueController {

    @Autowired
    private MovimentoEstoqueService movimentoEstoqueService;

    @PostMapping("/{codigoProduto}")
    public ResponseEntity<MovimentoEstoque> adicionarMovimento(
            @PathVariable String codigoProduto,
            @RequestBody MovimentoEstoque movimento) {

        MovimentoEstoque registrado = movimentoEstoqueService.registrarMovimento(codigoProduto, movimento);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrado);
    }
}
