package io.github.joaocastro20.estoqueNexdom.service;

import io.github.joaocastro20.estoqueNexdom.domain.enums.TipoProduto;
import io.github.joaocastro20.estoqueNexdom.repository.ProdutoRepository;
import io.github.joaocastro20.estoqueNexdom.domain.Produto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public Page<Produto> listarTodos(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto atualizarProduto(String codigo, Produto novoProduto) {
        Produto existente = produtoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Produto: " + codigo + " não foi encontrado."));

        existente.setDescricao(novoProduto.getDescricao());
        existente.setTipoProduto(novoProduto.getTipoProduto());
        existente.setValorFornecedor(novoProduto.getValorFornecedor());
        existente.setQuantidadeEstoque(novoProduto.getQuantidadeEstoque());

        return produtoRepository.save(existente);
    }

    public void excluirPorCodigo(String codigo) {
        produtoRepository.deleteByCodigo(codigo);
    }

    public List<Produto> buscarPorTipo(TipoProduto tipoProduto) {
        return produtoRepository.findByTipoProduto(tipoProduto);
    }

}

