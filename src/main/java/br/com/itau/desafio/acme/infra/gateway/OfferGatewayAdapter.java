package br.com.itau.desafio.acme.infra.gateway;

import br.com.itau.desafio.acme.core.application.gateway.OfferGateway;
import br.com.itau.desafio.acme.core.domain.Assistance;
import br.com.itau.desafio.acme.core.domain.Coverage;
import br.com.itau.desafio.acme.core.domain.MonthlyPremiumAmount;
import br.com.itau.desafio.acme.core.domain.Offer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Service
@Qualifier("offerGateway")
public class OfferGatewayAdapter implements OfferGateway {

    @Value("${app.catalog-service.url}")
    private String baseUrl;

    private final RestClient restClient = RestClient.builder()
            .messageConverters(converters -> new MappingJackson2HttpMessageConverter())
            .build();

    @Override
    public Offer getOfferById(UUID id) {
        return new Offer(
                UUID.fromString("adc56d77-348c-4bf0-908f-22d402ee715c"),
                UUID.fromString("1b2da7cc-b367-4196-8a78-9cfeec21f587"),
                "Seguro de Vida Familiar",
                LocalDate.now(),
                true,
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
