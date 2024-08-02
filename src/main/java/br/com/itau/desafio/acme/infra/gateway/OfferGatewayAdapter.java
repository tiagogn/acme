package br.com.itau.desafio.acme.infra.gateway;

import br.com.itau.desafio.acme.core.application.gateway.OfferGateway;
import br.com.itau.desafio.acme.core.domain.Assistance;
import br.com.itau.desafio.acme.core.domain.Coverage;
import br.com.itau.desafio.acme.core.domain.MonthlyPremiumAmount;
import br.com.itau.desafio.acme.core.domain.Offer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Qualifier("offerGateway")
@Slf4j
public class OfferGatewayAdapter implements OfferGateway {

    @Value("${app.catalog-service.url}")
    private String baseUrl;

    private final RestClient restClient = RestClient.create();

    @Override
    public Offer getOfferById(UUID id) {
        var offerResponse = restClient.get()
                .uri(baseUrl + "/offer/" + id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    log.error("Offer {} not found", id);
                })
                .body(OfferResponse.class);

        return offerResponse == null ? null : offerResponse.toOffer();
    }

    record OfferResponse(
            UUID id,
            UUID product_id,
            String name,
            LocalDateTime created_at,
            boolean active,
            Map<String, Double> coverages,
            Set<String> assistances,
            MonthlyPremiumAmountResponse monthly_premium_amount
    ){
        public Offer toOffer(){
            return new Offer(
                    id(),
                    product_id(),
                    name(),
                    created_at(),
                    this.active,
                    coverages().entrySet().stream()
                            .map(entry -> new Coverage(entry.getKey(), entry.getValue()))
                            .collect(Collectors.toSet()),
                    assistances().stream()
                            .map(Assistance::new)
                            .collect(Collectors.toSet()),
                    monthly_premium_amount().toMonthlyPremiumAmount()
            );
        }
    }

    record MonthlyPremiumAmountResponse(
            double max_amount,
            double min_amount,
            double suggested_amount
    ){
        public MonthlyPremiumAmount toMonthlyPremiumAmount(){
            return new MonthlyPremiumAmount(max_amount(), min_amount(), suggested_amount());
        }
    }
}
