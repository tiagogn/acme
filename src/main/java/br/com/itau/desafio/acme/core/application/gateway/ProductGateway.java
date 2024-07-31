package br.com.itau.desafio.acme.core.application.gateway;

import br.com.itau.desafio.acme.core.domain.Product;

import java.util.UUID;

public interface ProductGateway {

    public Product getProductById(UUID id);
}
