package br.com.itau.desafio.acme.core.domain;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class OfferFactory {

    public static Offer create(){
        return create(true);
    }

    public static Offer create(boolean active){
        return new Offer(
                UUID.fromString("adc56d77-348c-4bf0-908f-22d402ee715c"),
                UUID.fromString("1b2da7cc-b367-4196-8a78-9cfeec21f587"),
                "Seguro de Vida Familiar",
                LocalDateTime.of(2021, 07, 01, 0, 0),
                active,
                Set.of(new Coverage("Incêndio", 500000.00),
                        new Coverage("Desastres naturais", 600000.00),
                        new Coverage("Responsabiliadade civil", 80000.00),
                        new Coverage("Roubo", 100000.00)),
                Set.of(new Assistance("Encanador"),
                        new Assistance("Eletricista"),
                        new Assistance("Chaveiro 24h"),
                        new Assistance("Assistência Funerária")),
                new MonthlyPremiumAmount(100.74, 50.00, 60.25)
        );
    }
}
