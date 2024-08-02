package br.com.itau.desafio.acme.core.application;

import br.com.itau.desafio.acme.core.application.gateway.OfferGateway;
import br.com.itau.desafio.acme.core.application.gateway.ProductGateway;
import br.com.itau.desafio.acme.core.application.queue.InsuranceQuoteQueue;
import br.com.itau.desafio.acme.core.application.repository.InsuranceQuoteRepository;
import br.com.itau.desafio.acme.core.domain.InsuranceQuote;
import br.com.itau.desafio.acme.core.domain.ValidateInsuranceQuoteService;
import br.com.itau.desafio.acme.core.exception.InsuranceQuoteException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReceivedInsuranceQuote {

    private final OfferGateway offerGateway;

    private final ProductGateway productGateway;

    private final InsuranceQuoteRepository insuranceQuoteRepository;

    private final InsuranceQuoteQueue insuranceQuoteQueue;

    private final ValidateInsuranceQuoteService validateInsuranceQuoteService = new ValidateInsuranceQuoteService();

    public ReceivedInsuranceQuote(
            OfferGateway offerGateway,
            ProductGateway productGateway,
            InsuranceQuoteQueue insuranceQuoteQueue,
            InsuranceQuoteRepository insuranceQuoteRepository
    ) {
        this.offerGateway = offerGateway;
        this.productGateway = productGateway;
        this.insuranceQuoteRepository = insuranceQuoteRepository;
        this.insuranceQuoteQueue = insuranceQuoteQueue;
    }

    public void execute(InsuranceQuote insuranceQuote) throws InsuranceQuoteException {

        log.info("Received Insurance Quote: {}", insuranceQuote);

        if (insuranceQuoteRepository.existsByProductIdAndOfferIdAndCustomerDocument(insuranceQuote.getProductId(),
                insuranceQuote.getOfferId(), insuranceQuote.getCustomer().getDocumentNumber()))
            throw new InsuranceQuoteException("Insurance quote already exists");

        var product = productGateway.getProductById(insuranceQuote.getProductId());
        log.info("Product from ProductAPI: {}", product);

        var offer = offerGateway.getOfferById(insuranceQuote.getOfferId());
        log.info("Offer from OfferAPI: {}", offer);

        validateInsuranceQuoteService.validate(insuranceQuote, product, offer);
        log.info("Insurance Quote validated: {}", insuranceQuote);

        insuranceQuoteRepository.save(insuranceQuote);
        log.info("Insurance Quote saved: {}", insuranceQuote);

        insuranceQuoteQueue.publish(insuranceQuote);
        log.info("Insurance Quote published: {}", insuranceQuote);
    }
}
