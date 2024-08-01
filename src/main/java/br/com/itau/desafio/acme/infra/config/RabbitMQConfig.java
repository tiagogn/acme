package br.com.itau.desafio.acme.infra.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Value("${app.rabbit.insurance-quote.queue}")
    private String insuranceQuoteQueue;

    @Value("${app.rabbit.insurance-policy.queue}")
    private String insurancePolicyQueue;

    @Bean
    public Queue queue() {
        return new Queue(insuranceQuoteQueue, true);
    }

    @Bean
    public Exchange exchange() {
        return ExchangeBuilder
                .directExchange(insuranceQuoteQueue)
                .durable(true)
                .build();
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(insuranceQuoteQueue)
                .noargs();
    }

    @Bean
    public Queue insurancePolicyQueue() {
        return new Queue(insurancePolicyQueue, true);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMaxConcurrentConsumers(2);
        return factory;
    }
}
