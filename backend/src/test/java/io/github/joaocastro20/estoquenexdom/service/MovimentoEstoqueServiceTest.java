package io.github.joaocastro20.estoquenexdom.service;

import io.github.joaocastro20.estoquenexdom.domain.MovimentoEstoque;
import io.github.joaocastro20.estoquenexdom.domain.Produto;
import io.github.joaocastro20.estoquenexdom.domain.enums.TipoMovimentacao;
import io.github.joaocastro20.estoquenexdom.record.OrderRecord;
import io.github.joaocastro20.estoquenexdom.repository.MovimentoEstoqueRepository;
import io.github.joaocastro20.estoquenexdom.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovimentoEstoqueServiceTest {
    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private MovimentoEstoqueRepository movimentoEstoqueRepository;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private MovimentoEstoqueService movimentoEstoqueService;

    public MovimentoEstoqueServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveMovimentarEntradaComCodigoExistente() {
        Produto produto = new Produto();
        produto.setCodigo("001");
        produto.setQuantidadeEstoque(10);

        MovimentoEstoque movimentoEstoque = new MovimentoEstoque();
        movimentoEstoque.setId(1L);
        movimentoEstoque.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
        movimentoEstoque.setQuantidadeMovimentada(10);

        when(produtoRepository.findByCodigo("001")).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any())).thenReturn(produto);
        when(movimentoEstoqueRepository.save(any())).thenReturn(movimentoEstoque);

        MovimentoEstoque resultado = movimentoEstoqueService.registrarMovimento("001", movimentoEstoque);

        assertEquals(20, produto.getQuantidadeEstoque());
        assertEquals(produto, resultado.getProduto());
        verify(produtoRepository).save(produto);
        verify(movimentoEstoqueRepository).save(movimentoEstoque);
    }

    @Test
    void deveMovimentarSaidaComCodigoExistente() {
        Produto produto = new Produto();
        produto.setCodigo("001");
        produto.setQuantidadeEstoque(10);

        MovimentoEstoque movimentoEstoque = new MovimentoEstoque();
        movimentoEstoque.setId(1L);
        movimentoEstoque.setTipoMovimentacao(TipoMovimentacao.SAIDA);
        movimentoEstoque.setQuantidadeMovimentada(10);

        when(produtoRepository.findByCodigo("001")).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any())).thenReturn(produto);
        when(movimentoEstoqueRepository.save(any())).thenReturn(movimentoEstoque);

        MovimentoEstoque resultado = movimentoEstoqueService.registrarMovimento("001", movimentoEstoque);

        assertEquals(0, produto.getQuantidadeEstoque());
        assertEquals(produto, resultado.getProduto());
        verify(produtoRepository).save(produto);
        verify(movimentoEstoqueRepository).save(movimentoEstoque);
    }

    @Test
    void deveLancarErroSaidaInsuficiente() {
        Produto produto = new Produto();
        produto.setCodigo("001");
        produto.setQuantidadeEstoque(10);

        MovimentoEstoque movimentoEstoque = new MovimentoEstoque();
        movimentoEstoque.setId(1L);
        movimentoEstoque.setTipoMovimentacao(TipoMovimentacao.SAIDA);
        movimentoEstoque.setQuantidadeMovimentada(20);

        when(produtoRepository.findByCodigo("001")).thenReturn(Optional.of(produto));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            movimentoEstoqueService.registrarMovimento("001", movimentoEstoque);
        });

        assertEquals("Estoque insuficiente para saída.", ex.getMessage());
        verify(orderService).sendMessageOrder(any(OrderRecord.class));
        verify(produtoRepository, never()).save(any());
        verify(movimentoEstoqueRepository, never()).save(any());
    }

    @Test
    void deveLancarErroCodigoAusente() {
        MovimentoEstoque movimentoEstoque = new MovimentoEstoque();
        movimentoEstoque.setId(1L);
        movimentoEstoque.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
        movimentoEstoque.setQuantidadeMovimentada(10);

        when(produtoRepository.findByCodigo("INEXISTENTE")).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            movimentoEstoqueService.registrarMovimento("INEXISTENTE", movimentoEstoque);
        });

        assertEquals("Produto com código:  INEXISTENTE não encontrado.", ex.getMessage());
        verify(produtoRepository, never()).save(any());
        verify(movimentoEstoqueRepository, never()).save(any());
    }

    @Test
    void deveLancarErroSeTipoMovimentacaoInvalido() {
        Produto produto = new Produto();
        produto.setCodigo("ZZZ999");
        produto.setQuantidadeEstoque(10);

        MovimentoEstoque movimento = new MovimentoEstoque();
        movimento.setTipoMovimentacao(null);
        movimento.setQuantidadeMovimentada(1);

        when(produtoRepository.findByCodigo("ZZZ999")).thenReturn(Optional.of(produto));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            movimentoEstoqueService.registrarMovimento("ZZZ999", movimento);
        });

        assertEquals("Tipo de movimentação inválido.", ex.getMessage());
    }
}
