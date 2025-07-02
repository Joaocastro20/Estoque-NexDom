package io.github.joaocastro20.estoquenexdom.service;

import io.github.joaocastro20.estoquenexdom.domain.MovimentoEstoque;
import io.github.joaocastro20.estoquenexdom.domain.Produto;
import io.github.joaocastro20.estoquenexdom.domain.enums.TipoProduto;
import io.github.joaocastro20.estoquenexdom.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    public ProdutoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveListarCOmPaginacao() {
        List<MovimentoEstoque> movimentoEstoqueList = new ArrayList<>();
        Produto produto1 = new Produto(1L, "001", "testeD", TipoProduto.ELETRONICO, BigDecimal.valueOf(200), 12, movimentoEstoqueList);

        List<Produto> produtoList = List.of(produto1);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Produto> paginaMock = new PageImpl<>(produtoList);

        when(produtoRepository.findAll(pageable)).thenReturn(paginaMock);

        Page<Produto> result = produtoService.listarTodos(pageable);
        assertEquals(1, result.getTotalElements());
        assertEquals("testeD", result.getContent().get(0).getDescricao());
    }

    @Test
    void deveSalvar() {
        List<MovimentoEstoque> movimentoEstoqueList = new ArrayList<>();
        Produto produto1 = new Produto(1L, "001", "testeD", TipoProduto.ELETRONICO, BigDecimal.valueOf(200), 12, movimentoEstoqueList);

        when(produtoRepository.save(produto1)).thenReturn(produto1);

        Produto resultado = produtoService.salvar(produto1);
        assertNotNull(resultado);
        assertEquals(resultado.getCodigo(), produto1.getCodigo());
        verify(produtoRepository, times(1)).save(produto1);
    }

    @Test
    void deveAtualizarComCodigoExistente() {
        String codigo = "001";
        List<MovimentoEstoque> movimentoEstoqueList = new ArrayList<>();
        Produto existenteProduto = new Produto(1L, "001", "testeD", TipoProduto.ELETRONICO, BigDecimal.valueOf(200), 12, movimentoEstoqueList);
        Produto novoProduto = new Produto(1L, "001", "Nova Descricao", TipoProduto.ELETRONICO, BigDecimal.valueOf(200), 12, movimentoEstoqueList);

        when(produtoRepository.findByCodigo(codigo)).thenReturn(Optional.of(existenteProduto));
        when(produtoRepository.save(any(Produto.class))).thenAnswer(i -> i.getArgument(0));

        Produto atualizado = produtoService.atualizarProduto(codigo, novoProduto);

        assertEquals(novoProduto.getDescricao(), atualizado.getDescricao());
        assertEquals(novoProduto.getTipoProduto(), atualizado.getTipoProduto());
        assertEquals(novoProduto.getValorFornecedor(), atualizado.getValorFornecedor());
        assertEquals(novoProduto.getQuantidadeEstoque(), atualizado.getQuantidadeEstoque());
        verify(produtoRepository).findByCodigo(codigo);
        verify(produtoRepository).save(existenteProduto);

    }

    @Test
    void deveLancarErroComCodigoAusente() {
        String codigo = "001";
        List<MovimentoEstoque> movimentoEstoqueList = new ArrayList<>();
        Produto novoProduto = new Produto(1L, "001", "Nova Descricao", TipoProduto.ELETRONICO, BigDecimal.valueOf(200), 12, movimentoEstoqueList);

        when(produtoRepository.findByCodigo(codigo)).thenReturn(Optional.empty());
        when(produtoRepository.save(any(Produto.class))).thenAnswer(i -> i.getArgument(0));

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            produtoService.atualizarProduto(codigo, novoProduto);
        });

        assertEquals("Produto: 001 não foi encontrado.", ex.getMessage());
        verify(produtoRepository).findByCodigo(codigo);
        verify(produtoRepository, never()).save(any());


    }

    @Test
    void deveDeletarComCodigoEncontrado(){
        String codigo = "001";
        List<MovimentoEstoque> movimentoEstoqueList = new ArrayList<>();
        Produto existenteProduto = new Produto(1L, "001", "testeD", TipoProduto.ELETRONICO, BigDecimal.valueOf(200), 12, movimentoEstoqueList);

        when(produtoRepository.findByCodigo(codigo)).thenReturn(Optional.of(existenteProduto));
        doNothing().when(produtoRepository).delete(existenteProduto);

        produtoService.excluirPorCodigo(codigo);

        verify(produtoRepository).findByCodigo(codigo);
        verify(produtoRepository).delete(existenteProduto);
    }

    @Test
    void deveLancarErroAoTentarDeletarCodigoAusente(){
        String codigo = "001";
        List<MovimentoEstoque> movimentoEstoqueList = new ArrayList<>();
        Produto existenteProduto = new Produto(1L, "001", "testeD", TipoProduto.ELETRONICO, BigDecimal.valueOf(200), 12, movimentoEstoqueList);

        when(produtoRepository.findByCodigo(codigo)).thenReturn(Optional.empty());
        doNothing().when(produtoRepository).delete(existenteProduto);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            produtoService.excluirPorCodigo(codigo);
        });

        assertEquals("Produto com código '001' não encontrado.", ex.getMessage());
        verify(produtoRepository).findByCodigo(codigo);
        verify(produtoRepository, never()).delete(any());
    }

    @Test
    void deveBuscarPorTipo(){
        TipoProduto tipoProduto = TipoProduto.MOVEL;
        List<MovimentoEstoque> movimentoEstoqueList = new ArrayList<>();
        Produto existenteProduto1 = new Produto(1L, "001", "testeD", TipoProduto.MOVEL, BigDecimal.valueOf(200), 12, movimentoEstoqueList);
        Produto existenteProduto2 = new Produto(1L, "002", "teste2", TipoProduto.MOVEL, BigDecimal.valueOf(2002), 122, movimentoEstoqueList);

        List<Produto> listaMock = List.of(existenteProduto1, existenteProduto2);

        when(produtoRepository.findByTipoProduto(tipoProduto)).thenReturn(listaMock);

        List<Produto> resultado = produtoService.buscarPorTipo(tipoProduto);

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(p -> p.getTipoProduto() == tipoProduto));
        verify(produtoRepository).findByTipoProduto(tipoProduto);

    }
}
