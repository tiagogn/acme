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

    private Long id;

    private UUID insuracePolicyId;

    private Offer offer;

    private Category category;

    private LocalDateTime creaatedAt;

    private LocalDateTime updatedAt;

    private BigDecimal totalMonthlyPremiumAmount;

    private BigDecimal totalCoverageAmount;

    private Set<Coverage> coverages;

    private Set<Assistance> assistances;

    private Customer customer;
}
