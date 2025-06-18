package io.github.joaocastro20.estoqueNexdom.repository;

import io.github.joaocastro20.estoqueNexdom.domain.Produto;
import io.github.joaocastro20.estoqueNexdom.domain.enums.TipoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    void deleteByCodigo(String codigo);

    Optional<Produto> findByCodigo(String codigo);

    List<Produto> findByTipoProduto(TipoProduto tipoProduto);
}