package br.com.itau.desafio.acme.infra.config;

import br.com.itau.desafio.acme.core.application.GetInsuranceQuote;
import br.com.itau.desafio.acme.core.application.ReceivedInsuranceQuote;
import br.com.itau.desafio.acme.core.application.UpdateInsuranceQuoteByPolice;
import br.com.itau.desafio.acme.core.application.gateway.OfferGateway;
import br.com.itau.desafio.acme.core.application.gateway.ProductGateway;
import br.com.itau.desafio.acme.core.application.queue.InsuranceQuoteQueue;
import br.com.itau.desafio.acme.core.application.repository.InsuranceQuoteRepository;
import br.com.itau.desafio.acme.infra.queue.InsuranceQuoteQueueAdapter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigBeans {

    private final ProductGateway productGateway;

    private final OfferGateway offerGateway;

    private final InsuranceQuoteRepository insuranceQuoteRepository;

    private final RabbitTemplate rabbitTemplate;

    public ConfigBeans(
            ProductGateway productGateway,
            OfferGateway offerGateway,
            InsuranceQuoteRepository insuranceQuoteRepository,
            RabbitTemplate rabbitTemplate
    ) {
        this.productGateway = productGateway;
        this.offerGateway = offerGateway;
        this.insuranceQuoteRepository = insuranceQuoteRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Bean
    public ReceivedInsuranceQuote receivedInsuranceQuote () {
        return new ReceivedInsuranceQuote(
                offerGateway,
                productGateway,
                insuranceQuoteQueue(),
                insuranceQuoteRepository
        );
    }

    @Bean
    public GetInsuranceQuote getInsuranceQuote() {
        return new GetInsuranceQuote(
                insuranceQuoteRepository
        );
    }

    @Bean
    public UpdateInsuranceQuoteByPolice updateInsuranceQuoteByPolice() {
        return new UpdateInsuranceQuoteByPolice(
                insuranceQuoteRepository
        );
    }

    @Bean
    public InsuranceQuoteQueue insuranceQuoteQueue() {
        return new InsuranceQuoteQueueAdapter(
                rabbitTemplate,
                updateInsuranceQuoteByPolice()
        );
    }

}
