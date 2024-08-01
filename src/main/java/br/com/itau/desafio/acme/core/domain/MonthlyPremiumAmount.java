package br.com.itau.desafio.acme.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class MonthlyPremiumAmount {

    private BigDecimal maxAmount;

    private BigDecimal minAmount;

    private BigDecimal suggestedAmount;

    public MonthlyPremiumAmount(BigDecimal maxAmount, BigDecimal minAmount, BigDecimal suggestedAmount) {
        this.maxAmount = maxAmount;
        this.minAmount = minAmount;
        this.suggestedAmount = suggestedAmount;
    }

    public MonthlyPremiumAmount(double maxAmount, double minAmount, double suggestedAmount) {
        this.maxAmount = BigDecimal.valueOf(maxAmount);
        this.minAmount = BigDecimal.valueOf(minAmount);
        this.suggestedAmount = BigDecimal.valueOf(suggestedAmount);
    }
}
