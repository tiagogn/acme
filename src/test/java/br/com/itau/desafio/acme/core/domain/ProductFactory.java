package br.com.itau.desafio.acme.core.domain;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public class ProductFactory {

    public static Product createProdut() {
        return createProdut(true);
    }


    public static Product createProdut(boolean active) {
        return new Product(
                UUID.fromString("1b2da7cc-b367-4196-8a78-9cfeec21f587"),
                "Seguro de Vida",
                LocalDate.of(2021, 07, 01),
                true,
                Set.of(UUID.fromString("adc56d77-348c-4bf0-908f-22d402ee715c"),
                        UUID.fromString("bdc56d77-348c-4bf0-908f-22d402ee715c"),
                        UUID.fromString("cdc56d77-348c-4bf0-908f-22d402ee715c"))
        );
    }
}
