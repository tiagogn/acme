package br.com.itau.desafio.acme.infra.gateway;

import br.com.itau.desafio.acme.core.application.gateway.ProductGateway;
import br.com.itau.desafio.acme.core.domain.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Service
@Qualifier("productGateway")
public class ProductGatewayAdapter implements ProductGateway {

    @Value("${app.catalog-service.url}")
    private String baseUrl;

    private final RestClient restClient = RestClient.builder()
            .messageConverters(converters -> new MappingJackson2HttpMessageConverter())
            .build();

    @Override
    public Product getProductById(UUID id) {
        return new Product(
                UUID.fromString("1b2da7cc-b367-4196-8a78-9cfeec21f587"),
                "Seguro de Vida",
                LocalDate.now(),
                true,
                Set.of(UUID.fromString("adc56d77-348c-4bf0-908f-22d402ee715c"),
                        UUID.fromString("bdc56d77-348c-4bf0-908f-22d402ee715c"),
                        UUID.fromString("cdc56d77-348c-4bf0-908f-22d402ee715c"))
        );
    }
}
