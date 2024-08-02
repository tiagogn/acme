package br.com.itau.desafio.acme.core.application.gateway;

import br.com.itau.desafio.acme.core.domain.Offer;

import java.util.UUID;

public interface OfferGateway {

    Offer getOfferById(UUID id);
}
