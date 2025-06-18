package io.github.joaocastro20.estoqueNexdom.controller;

import io.github.joaocastro20.estoqueNexdom.domain.Produto;
import io.github.joaocastro20.estoqueNexdom.domain.enums.TipoProduto;
import io.github.joaocastro20.estoqueNexdom.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<Page<Produto>> listarTodos(Pageable pageable) {
        return ResponseEntity.ok(produtoService.listarTodos(pageable));
    }

    @PostMapping
    public ResponseEntity<Produto> salvar(@RequestBody Produto produto) {
        return ResponseEntity.ok(produtoService.salvar(produto));
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Produto> atualizarProduto(
            @PathVariable String codigo,
            @RequestBody Produto produto) {

        Produto atualizado = produtoService.atualizarProduto(codigo, produto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletarPorCodigo(@PathVariable String codigo) {
        produtoService.excluirPorCodigo(codigo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Produto>> buscarPorTipo(@PathVariable TipoProduto tipo) {
        List<Produto> produtos = produtoService.buscarPorTipo(tipo);
        return ResponseEntity.ok(produtos);
    }
}