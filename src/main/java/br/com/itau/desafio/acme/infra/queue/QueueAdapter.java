package br.com.itau.desafio.acme.infra.queue;

import br.com.itau.desafio.acme.core.application.queue.Queue;
import br.com.itau.desafio.acme.core.domain.InsuranceQuote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Qualifier("queue")
@Slf4j
public class QueueAdapter implements Queue<InsuranceQuote> {

    @Value("${app.rabbit.insurance-quote.queue}")
    private String queueName;

    private final RabbitTemplate rabbitTemplate;

    public QueueAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(InsuranceQuote message) {
        rabbitTemplate.convertAndSend(queueName, message);
        log.info("Message published: {}", message);
    }

    @Override
    public void receive(InsuranceQuote message) {

    }

}
