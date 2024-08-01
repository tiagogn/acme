package br.com.itau.desafio.acme.core.application.gateway;

import br.com.itau.desafio.acme.core.domain.Offer;

import java.util.UUID;

public interface OfferGateway {

    public Offer getOfferById(UUID id);
}
