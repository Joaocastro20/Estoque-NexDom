package io.github.joaocastro20.estoquenexdom.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mensagem {
    private Long id;
    private String name;
    private String description;
    private BigDecimal amount;
}
