package br.com.itau.desafio.acme.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsuranceQuote {

    private Integer id;

    private Integer insurancePolicyId;

    private UUID productId;

    private UUID offerId;

    private Category category;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private BigDecimal totalMonthlyPremiumAmount;

    private BigDecimal totalCoverageAmount;

    private Set<Coverage> coverages;

    private Set<Assistance> assistances;

    private Customer customer;

    public BigDecimal getTotalSumOfCoverage() {
        return this.coverages.stream().map(Coverage::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
