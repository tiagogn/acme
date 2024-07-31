package br.com.itau.desafio.acme.core.application;

import br.com.itau.desafio.acme.core.application.queue.Queue;
import br.com.itau.desafio.acme.core.application.repository.InsuranceQuoteRepository;
import br.com.itau.desafio.acme.core.domain.InsuranceQuote;

public class UpdateInsuranceQuoteByPolice {

    private InsuranceQuoteRepository insuranceQuoteRepository;

    private Queue<InsuranceQuote> queue;

    public UpdateInsuranceQuoteByPolice(
            InsuranceQuoteRepository insuranceQuoteRepository,
            Queue<InsuranceQuote> queue
    ) {
        this.insuranceQuoteRepository = insuranceQuoteRepository;
        this.queue = queue;
    }

    public void execute() {

    }
}
