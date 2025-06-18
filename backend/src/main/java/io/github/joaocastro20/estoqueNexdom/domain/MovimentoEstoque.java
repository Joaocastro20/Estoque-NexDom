package io.github.joaocastro20.estoqueNexdom.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.github.joaocastro20.estoqueNexdom.domain.enums.TipoMovimentacao;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimento_estoque")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimentoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_id")
    @JsonBackReference
    private Produto produto;

    @Column(name = "tipo_movimentacao", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipoMovimentacao;

    @Column(name = "valor_venda", precision = 10, scale = 2)
    private BigDecimal valorVenda;

    @Column(name = "data_venda", nullable = false)
    private LocalDateTime dataVenda = LocalDateTime.now();

    @Column(name = "quantidade_movimentada", nullable = false)
    private Integer quantidadeMovimentada;
}