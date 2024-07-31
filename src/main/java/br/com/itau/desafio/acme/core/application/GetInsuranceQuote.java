package br.com.itau.desafio.acme.core.application;

import br.com.itau.desafio.acme.core.application.repository.InsuranceQuoteRepository;
import br.com.itau.desafio.acme.core.domain.InsuranceQuote;
import br.com.itau.desafio.acme.core.exception.InsuranceQuoteException;

import java.util.UUID;

public class GetInsuranceQuote {

    private final InsuranceQuoteRepository insuranceQuoteRepository;

    public GetInsuranceQuote(
        InsuranceQuoteRepository insuranceQuoteRepository
    ){
        this.insuranceQuoteRepository = insuranceQuoteRepository;
    }

    public InsuranceQuote execute(UUID insurancePolicyId) {
        return insuranceQuoteRepository.findByInsurancePolicyId(insurancePolicyId).orElseThrow(() -> new InsuranceQuoteException("Insurance Quote not found"));
    }
}
