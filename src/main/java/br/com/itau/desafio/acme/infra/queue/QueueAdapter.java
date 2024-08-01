package br.com.itau.desafio.acme.infra.queue;

import br.com.itau.desafio.acme.core.application.queue.Queue;
import br.com.itau.desafio.acme.core.domain.InsuranceQuote;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("queue")
public class QueueAdapter implements Queue<InsuranceQuote> {

    /*private final RabbitTemplate rabbitTemplate;

    private final String exchange;

    private final String routingKey;

    public QueueAdapter(RabbitTemplate rabbitTemplate, String exchange, String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }*/

    @Override
    public void publish(String queue, InsuranceQuote message) {

    }

    @Override
    public void receive(String queue, InsuranceQuote message) {

    }
}
