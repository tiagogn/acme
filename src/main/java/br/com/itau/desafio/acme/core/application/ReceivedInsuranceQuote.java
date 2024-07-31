package br.com.itau.desafio.acme.core.application;

import br.com.itau.desafio.acme.core.application.gateway.OfferGateway;
import br.com.itau.desafio.acme.core.application.gateway.ProductGateway;
import br.com.itau.desafio.acme.core.application.queue.Queue;
import br.com.itau.desafio.acme.core.application.repository.InsuranceQuoteRepository;
import br.com.itau.desafio.acme.core.domain.InsuranceQuote;
import br.com.itau.desafio.acme.core.domain.ValidateInsuranceQuoteService;
import br.com.itau.desafio.acme.core.exception.InsuranceQuoteException;

public class ReceivedInsuranceQuote {

    private static final String INSURANCE_QUOTE_RECEIVED = "insuranceQuoteReceveid";

    private final OfferGateway offerGateway;

    private final ProductGateway productGateway;

    private final InsuranceQuoteRepository insuranceQuoteRepository;

    private final Queue<InsuranceQuote> queue;

    private final ValidateInsuranceQuoteService validateInsuranceQuoteService = new ValidateInsuranceQuoteService();

    public ReceivedInsuranceQuote(
            OfferGateway offerGateway,
            ProductGateway productGateway,
            Queue<InsuranceQuote> queue,
            InsuranceQuoteRepository insuranceQuoteRepository
    ) {
        this.offerGateway = offerGateway;
        this.productGateway = productGateway;
        this.insuranceQuoteRepository = insuranceQuoteRepository;
        this.queue = queue;
    }

    public void execute(InsuranceQuote insuranceQuote) throws InsuranceQuoteException {

        var product = productGateway.getProductById(insuranceQuote.getOffer().getProductId());
        var offer = offerGateway.getOfferById(insuranceQuote.getOffer().getId());

        validateInsuranceQuoteService.validate(insuranceQuote, product, offer);

        insuranceQuoteRepository.save(insuranceQuote);
        queue.publish(INSURANCE_QUOTE_RECEIVED, insuranceQuote);
    }
}
