package br.com.itau.desafio.acme.core.application;

import br.com.itau.desafio.acme.core.application.repository.InsuranceQuoteRepository;
import br.com.itau.desafio.acme.core.domain.InsuranceQuote;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class GetInsuranceQuote {

    private final InsuranceQuoteRepository insuranceQuoteRepository;

    public GetInsuranceQuote(
        InsuranceQuoteRepository insuranceQuoteRepository
    ){
        this.insuranceQuoteRepository = insuranceQuoteRepository;
    }

    public InsuranceQuote getByInsuranceId(UUID insurancePolicyId) {
        log.info("GetInsuranceQuote.getByInsuranceId: insurancePolicyId={}", insurancePolicyId);
        return insuranceQuoteRepository.findByInsurancePolicyId(insurancePolicyId).orElse(null);
    }

    public InsuranceQuote getById(Integer id) {
        log.info("GetInsuranceQuote.getById: id={}", id);
        return insuranceQuoteRepository.findById(id).orElse(null);
    }
}
