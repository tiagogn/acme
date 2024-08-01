package br.com.itau.desafio.acme.infra.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "coverage")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoverageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "insurance_quote_id", nullable = false)
    private InsuranceQuoteEntity insuranceQuoteEntity;

    private String name;

    private BigDecimal value;

    public CoverageEntity(InsuranceQuoteEntity insuranceQuoteEntity, String name, BigDecimal value) {
        this.insuranceQuoteEntity = insuranceQuoteEntity;
        this.name = name;
        this.value = value;
    }
}
