package br.com.itau.desafio.acme.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Offer {

    private UUID id;

    private UUID productId;

    private String name;

    private LocalDateTime createdAt;

    private boolean active;

    private Set<Coverage> coverages;

    private Set<Assistance> assistances;

    private MonthlyPremiumAmount monthlyPremiumAmount;
}
