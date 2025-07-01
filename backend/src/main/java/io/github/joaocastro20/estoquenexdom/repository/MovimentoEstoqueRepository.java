package io.github.joaocastro20.estoquenexdom.repository;

import io.github.joaocastro20.estoquenexdom.domain.MovimentoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentoEstoqueRepository extends JpaRepository<MovimentoEstoque, Long> {
}

