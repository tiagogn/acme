package br.com.itau.desafio.acme.infra.config;

import br.com.itau.desafio.acme.core.application.GetInsuranceQuote;
import br.com.itau.desafio.acme.core.application.ReceivedInsuranceQuote;
import br.com.itau.desafio.acme.core.application.gateway.OfferGateway;
import br.com.itau.desafio.acme.core.application.gateway.ProductGateway;
import br.com.itau.desafio.acme.core.application.queue.Queue;
import br.com.itau.desafio.acme.core.application.repository.InsuranceQuoteRepository;
import br.com.itau.desafio.acme.core.domain.InsuranceQuote;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigBeans {

    private final ProductGateway productGateway;

    private final OfferGateway offerGateway;

    private final Queue<InsuranceQuote> queue;

    private final InsuranceQuoteRepository insuranceQuoteRepository;

    @Value("${app.catalog-service.url}")
    private String baseUrl;

    public ConfigBeans(
            ProductGateway productGateway,
            OfferGateway offerGateway,
            Queue<InsuranceQuote> queue,
            InsuranceQuoteRepository insuranceQuoteRepository
    ) {
        this.productGateway = productGateway;
        this.offerGateway = offerGateway;
        this.queue = queue;
        this.insuranceQuoteRepository = insuranceQuoteRepository;
    }

    @Bean
    public ReceivedInsuranceQuote receivedInsuranceQuote() {
        return new ReceivedInsuranceQuote(
                offerGateway,
                productGateway,
                queue,
                insuranceQuoteRepository
        );
    }

    @Bean
    public GetInsuranceQuote getInsuranceQuote() {
        return new GetInsuranceQuote(
                insuranceQuoteRepository
        );
    }

}
