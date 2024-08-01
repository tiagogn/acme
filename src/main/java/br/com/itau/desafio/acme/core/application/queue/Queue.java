package br.com.itau.desafio.acme.core.application.queue;

public interface Queue<T> {
    void publish(T message);
    void receive(T message);
}
