package br.com.itau.desafio.acme.core.application.queue;

import br.com.itau.desafio.acme.core.domain.InsuranceQuote;
import br.com.itau.desafio.acme.core.dto.InsurancePolicyIssued;

public interface InsuranceQuoteQueue {
    void publish(InsuranceQuote insuranceQuote);
    void receive(InsurancePolicyIssued insurancePolicyIssued);
}
