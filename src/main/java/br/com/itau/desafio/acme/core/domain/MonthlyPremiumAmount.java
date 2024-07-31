package br.com.itau.desafio.acme.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyPremiumAmount {

    private BigDecimal maxAmount;

    private BigDecimal minAmount;

    private BigDecimal suggestedAmount;
}
