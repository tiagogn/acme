package br.com.itau.desafio.acme.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Coverage {

    private String name;

    private BigDecimal value;

    public Coverage(String name, BigDecimal value) {
        this.name = name;
        this.value = value;
    }

    public Coverage(String name, double value) {
        this.name = name;
        this.value = BigDecimal.valueOf(value);
    }
}
