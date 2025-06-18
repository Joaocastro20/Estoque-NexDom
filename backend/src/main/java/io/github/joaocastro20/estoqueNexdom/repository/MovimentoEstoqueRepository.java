package io.github.joaocastro20.estoqueNexdom.repository;

import io.github.joaocastro20.estoqueNexdom.domain.MovimentoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentoEstoqueRepository extends JpaRepository<MovimentoEstoque, Long> {
}

