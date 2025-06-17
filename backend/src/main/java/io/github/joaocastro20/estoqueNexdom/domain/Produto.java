package io.github.joaocastro20.estoqueNexdom.domain;

import io.github.joaocastro20.estoqueNexdom.domain.MovimentoEstoque;
import io.github.joaocastro20.estoqueNexdom.domain.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "produto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String codigo;

    @Column(nullable = false, length = 255)
    private String descricao;

    @Column(name = "tipo_produto", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private TipoProduto tipoProduto;

    @Column(name = "valor_fornecedor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorFornecedor;

    @Column(name = "quantidade_estoque", nullable = false)
    private Integer quantidadeEstoque;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovimentoEstoque> movimentos;
}