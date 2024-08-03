package br.com.itau.desafio.acme.core.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class InsuranceQuoteFactory {

    public static InsuranceQuote createInsuranceQuote() {
        return new InsuranceQuote(
                0,
                0,
                UUID.fromString("1b2da7cc-b367-4196-8a78-9cfeec21f587"),
                UUID.fromString("adc56d77-348c-4bf0-908f-22d402ee715c"),
                Category.HOME,
                LocalDateTime.now(),
                LocalDateTime.now(),
                BigDecimal.valueOf(75.25),
                BigDecimal.valueOf(825000.00),
                Set.of(new Coverage("Incêndio", BigDecimal.valueOf(250000.00)),
                        new Coverage("Desastres naturais", BigDecimal.valueOf(500000.00)),
                        new Coverage("Responsabiliadade civil", BigDecimal.valueOf(75000.00))),
                Set.of(new Assistance("Encanador"), new Assistance("Eletricista"), new Assistance("Chaveiro 24h")),
                new Customer(
                       "11111111111",
                        "Fulano de Tal",
                        "NATURAL",
                        Gender.MALE,
                        LocalDate.of(1980, 1, 1),
                        "fulanodetal@email.com",
                        "11999999999"
                )
        );

    }
}
