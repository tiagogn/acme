package br.com.itau.desafio.acme.infra.gateway;

import br.com.itau.desafio.acme.core.application.gateway.ProductGateway;
import br.com.itau.desafio.acme.core.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Service
@Qualifier("productGateway")
@Slf4j
public class ProductGatewayAdapter implements ProductGateway {

    @Value("${app.catalog-service.url}")
    private String baseUrl;

    private final RestClient restClient = RestClient.create();

    @Override
    public Product getProductById(UUID id) {
        return restClient.get()
                .uri(baseUrl + "/product/" + id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> log.info("Product " + id + " not found"))
                .body(Product.class);

    }
}
