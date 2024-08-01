package br.com.itau.desafio.acme.core.application;

import br.com.itau.desafio.acme.core.application.repository.InsuranceQuoteRepository;
import br.com.itau.desafio.acme.core.domain.InsuranceQuote;

import java.util.UUID;

public class GetInsuranceQuote {

    private final InsuranceQuoteRepository insuranceQuoteRepository;

    public GetInsuranceQuote(
        InsuranceQuoteRepository insuranceQuoteRepository
    ){
        this.insuranceQuoteRepository = insuranceQuoteRepository;
    }

    public InsuranceQuote getByInsuranceId(UUID insurancePolicyId) {
        return insuranceQuoteRepository.findByInsurancePolicyId(insurancePolicyId).orElse(null);
    }

    public InsuranceQuote getById(Long id) {
        return insuranceQuoteRepository.findById(id).orElse(null);
    }
}
