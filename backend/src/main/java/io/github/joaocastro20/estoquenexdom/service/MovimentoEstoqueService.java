package io.github.joaocastro20.estoquenexdom.service;

import io.github.joaocastro20.estoquenexdom.domain.MovimentoEstoque;
import io.github.joaocastro20.estoquenexdom.domain.Produto;
import io.github.joaocastro20.estoquenexdom.domain.enums.TipoMovimentacao;
import io.github.joaocastro20.estoquenexdom.record.OrderRecord;
import io.github.joaocastro20.estoquenexdom.repository.MovimentoEstoqueRepository;
import io.github.joaocastro20.estoquenexdom.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovimentoEstoqueService {

    private final MovimentoEstoqueRepository movimentoEstoqueRepository;

    private final ProdutoRepository produtoRepository;

    private final OrderService orderService;


    public MovimentoEstoque registrarMovimento(String codigoProduto, MovimentoEstoque movimento) {
        Produto produto = produtoRepository.findByCodigo(codigoProduto)
                .orElseThrow(() -> new EntityNotFoundException("Produto com código:  " + codigoProduto + " não encontrado."));

        if (movimento.getTipoMovimentacao() == TipoMovimentacao.ENTRADA) {
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + movimento.getQuantidadeMovimentada());
        } else if (movimento.getTipoMovimentacao() == TipoMovimentacao.SAIDA) {
            int novaQuantidade = produto.getQuantidadeEstoque() - movimento.getQuantidadeMovimentada();
            if (novaQuantidade < 0) {
                OrderRecord order = new OrderRecord(produto.getId(), produto.getCodigo(), produto.getDescricao(), movimento.getQuantidadeMovimentada().doubleValue());
                orderService.sendMessageOrder(order);
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
