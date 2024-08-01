package br.com.itau.desafio.acme.infra.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "insurance_quote")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class InsuranceQuoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "insurance_quote_id_seq")
    @SequenceGenerator(name = "insurance_quote_id_seq", sequenceName = "insurance_quote_id_seq", allocationSize = 1)
    @EqualsAndHashCode.Include
    private Integer id;

    private Integer insurancePolicyId;

    private UUID productId;

    private UUID offerId;

    private String category;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private BigDecimal totalMonthlyPremiumAmount;

    private BigDecimal totalCoverageAmount;

    @OneToMany(mappedBy = "insuranceQuoteEntity", cascade = CascadeType.ALL)
    private Set<CoverageEntity> coverageEntities = new HashSet<>();

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "insurance_quote_assistances", joinColumns = @JoinColumn(name = "insurance_quote_id"))
    private Set<String> assistances = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customerEntity;
}
