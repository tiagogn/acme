package br.com.itau.desafio.acme.infra.gateway;

import br.com.itau.desafio.acme.core.application.gateway.ProductGateway;
import br.com.itau.desafio.acme.core.domain.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ProductGatewayAdapterInMemory implements ProductGateway {

    @Override
    public Product getProductById(UUID id) {
        return inMemory.get(id);
    }

    private static final Map<UUID, Product> inMemory = Map.of(
            UUID.fromString("1b2da7cc-b367-4196-8a78-9cfeec21f587"),
            new Product(
                    UUID.fromString("1b2da7cc-b367-4196-8a78-9cfeec21f587"),
                    "Seguro de Vida",
                    LocalDate.of(2021,07,01),
                    true,
                    Set.of(UUID.fromString("adc56d77-348c-4bf0-908f-22d402ee715c"),
                            UUID.fromString("bdc56d77-348c-4bf0-908f-22d402ee715c"),
                            UUID.fromString("cdc56d77-348c-4bf0-908f-22d402ee715c"))
            )
    );
}

