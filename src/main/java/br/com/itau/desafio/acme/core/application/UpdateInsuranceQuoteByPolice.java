package br.com.itau.desafio.acme.core.application;

import br.com.itau.desafio.acme.core.application.repository.InsuranceQuoteRepository;
import br.com.itau.desafio.acme.core.dto.InsurancePolicyIssued;
import br.com.itau.desafio.acme.core.exception.InsuranceQuoteNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UpdateInsuranceQuoteByPolice {

    private final InsuranceQuoteRepository insuranceQuoteRepository;

    public UpdateInsuranceQuoteByPolice(
            InsuranceQuoteRepository insuranceQuoteRepository
    ) {
        this.insuranceQuoteRepository = insuranceQuoteRepository;
    }

    public void execute(InsurancePolicyIssued insurancePolicyIssued) throws InsuranceQuoteNotFoundException {

        log.info("UpdateInsuranceQuoteByPolice.execute: insurancePolicyIssued={}", insurancePolicyIssued);

        var insuranceQuote = insuranceQuoteRepository.findById(insurancePolicyIssued.insuranceQuoteId())
                .orElseThrow(() -> new InsuranceQuoteNotFoundException("Insurance Quote not found"));

        insuranceQuote.setInsurancePolicyId(insurancePolicyIssued.insurancePolicyId());

        insuranceQuoteRepository.save(insuranceQuote);
        log.info("Insurance Quote updated: {}", insuranceQuote);
    }

}
