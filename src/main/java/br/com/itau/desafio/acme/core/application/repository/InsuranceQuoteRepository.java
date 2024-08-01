package br.com.itau.desafio.acme.core.application.repository;

import br.com.itau.desafio.acme.core.domain.InsuranceQuote;

import java.util.Optional;
import java.util.UUID;

public interface InsuranceQuoteRepository {
    void save(InsuranceQuote insuranceQuote);
    Optional<InsuranceQuote> findById(Long id);
    Optional<InsuranceQuote> findByInsurancePolicyId(UUID insurancePolicyId);
    boolean existsByProductIdAndOfferIdAndCustomerDocument(UUID productId, UUID offerId, String documentNumber);
}
