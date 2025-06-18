package io.github.joaocastro20.estoqueNexdom.service;

import io.github.joaocastro20.estoqueNexdom.domain.MovimentoEstoque;
import io.github.joaocastro20.estoqueNexdom.domain.Produto;
import io.github.joaocastro20.estoqueNexdom.domain.enums.TipoMovimentacao;
import io.github.joaocastro20.estoqueNexdom.repository.MovimentoEstoqueRepository;
import io.github.joaocastro20.estoqueNexdom.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovimentoEstoqueService {

    private final MovimentoEstoqueRepository movimentoEstoqueRepository;

    private final ProdutoRepository produtoRepository;


    public MovimentoEstoque registrarMovimento(String codigoProduto, MovimentoEstoque movimento) {
        Produto produto = produtoRepository.findByCodigo(codigoProduto)
                .orElseThrow(() -> new EntityNotFoundException("Produto com código:  " + codigoProduto + " não encontrado."));

        if (movimento.getTipoMovimentacao() == TipoMovimentacao.ENTRADA) {
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + movimento.getQuantidadeMovimentada());
        } else if (movimento.getTipoMovimentacao() == TipoMovimentacao.SAÍDA) {
            int novaQuantidade = produto.getQuantidadeEstoque() - movimento.getQuantidadeMovimentada();
            if (novaQuantidade < 0) {
                throw new IllegalArgumentException("Estoque insuficiente para saída.");
            }
            produto.setQuantidadeEstoque(novaQuantidade);
        } else {
            throw new IllegalArgumentException("Tipo de movimentação inválido.");
        }

        movimento.setProduto(produto);
        produtoRepository.save(produto);
        return movimentoEstoqueRepository.save(movimento);
    }
}
