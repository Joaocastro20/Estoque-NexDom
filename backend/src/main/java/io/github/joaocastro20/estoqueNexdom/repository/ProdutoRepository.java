package io.github.joaocastro20.estoqueNexdom.repository;

import io.github.joaocastro20.estoqueNexdom.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}