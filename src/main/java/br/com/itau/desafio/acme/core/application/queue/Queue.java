package br.com.itau.desafio.acme.core.application.queue;

public interface Queue<T> {
    void publish(String queue, T message);
    void receive(String queue, T message);
}
