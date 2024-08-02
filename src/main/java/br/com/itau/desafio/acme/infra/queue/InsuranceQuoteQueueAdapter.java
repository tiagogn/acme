package br.com.itau.desafio.acme.infra.queue;

import br.com.itau.desafio.acme.core.application.queue.InsuranceQuoteQueue;
import br.com.itau.desafio.acme.core.domain.InsuranceQuote;
import br.com.itau.desafio.acme.core.dto.InsurancePolicyIssued;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.function.Consumer;

@Slf4j
public class InsuranceQuoteQueueAdapter implements InsuranceQuoteQueue {

    @Value("${app.rabbit.insurance-quote.queue}")
    private String queueName;

    private final RabbitTemplate rabbitTemplate;

    private final Consumer<InsurancePolicyIssued> updateInsuranceQuoteByPolice;

    public InsuranceQuoteQueueAdapter(RabbitTemplate rabbitTemplate, Consumer<InsurancePolicyIssued> updateInsuranceQuoteByPolice) {
        this.rabbitTemplate = rabbitTemplate;
        this.updateInsuranceQuoteByPolice = updateInsuranceQuoteByPolice;
    }

    @Override
    public void publish(InsuranceQuote insuranceQuote) {
        rabbitTemplate.convertAndSend(queueName, insuranceQuote);
        log.info("Insurance Quote message published: {}", insuranceQuote);
    }

    @Override
    @RabbitListener(queues = "${app.rabbit.insurance-policy.queue}")
    public void receive(@Payload InsurancePolicyIssued insurancePolicyIssued) {

        log.info("Message received from Policy Issued: {}", insurancePolicyIssued);

        try {
            updateInsuranceQuoteByPolice.accept(insurancePolicyIssued);
            log.info("Insurance Quote updated by police: {}", insurancePolicyIssued);
        } catch (Exception e) {
            log.error("Error to update insurance quote by police: {}", e.getMessage());
            //could be sent to a dead letter queue
        }
    }

}
