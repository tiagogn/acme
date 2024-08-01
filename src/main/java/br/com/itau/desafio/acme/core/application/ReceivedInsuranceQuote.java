package br.com.itau.desafio.acme.core.application;

import br.com.itau.desafio.acme.core.application.gateway.OfferGateway;
import br.com.itau.desafio.acme.core.application.gateway.ProductGateway;
import br.com.itau.desafio.acme.core.application.queue.Queue;
import br.com.itau.desafio.acme.core.application.repository.InsuranceQuoteRepository;
import br.com.itau.desafio.acme.core.domain.InsuranceQuote;
import br.com.itau.desafio.acme.core.domain.ValidateInsuranceQuoteService;
import br.com.itau.desafio.acme.core.exception.InsuranceQuoteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class ReceivedInsuranceQuote {

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

        if (insuranceQuoteRepository.existsByProductIdAndOfferIdAndCustomerDocument(insuranceQuote.getProductId(),
                insuranceQuote.getOfferId(), insuranceQuote.getCustomer().getDocumentNumber()))
            throw new InsuranceQuoteException("Insurance quote already exists");

        var product = productGateway.getProductById(insuranceQuote.getProductId());
        log.info("Product: {}", product);

        var offer = offerGateway.getOfferById(insuranceQuote.getOfferId());
        log.info("Offer: {}", offer);

        validateInsuranceQuoteService.validate(insuranceQuote, product, offer);

        insuranceQuoteRepository.save(insuranceQuote);

        queue.publish(insuranceQuote);
    }
}
