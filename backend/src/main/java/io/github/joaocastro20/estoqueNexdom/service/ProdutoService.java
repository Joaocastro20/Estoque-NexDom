package io.github.joaocastro20.estoqueNexdom.service;

import io.github.joaocastro20.estoqueNexdom.repository.ProdutoRepository;
import io.github.joaocastro20.estoqueNexdom.domain.Produto;
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

}

