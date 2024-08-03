package br.com.itau.desafio.acme.infra.http;

import br.com.itau.desafio.acme.AcmeApplicationTests;
import br.com.itau.desafio.acme.core.application.gateway.OfferGateway;
import br.com.itau.desafio.acme.core.application.gateway.ProductGateway;
import br.com.itau.desafio.acme.core.application.queue.InsuranceQuoteQueue;
import br.com.itau.desafio.acme.core.application.repository.InsuranceQuoteRepository;
import br.com.itau.desafio.acme.core.domain.Category;
import br.com.itau.desafio.acme.core.domain.factory.InsuranceQuoteFactory;
import br.com.itau.desafio.acme.core.domain.factory.OfferFactory;
import br.com.itau.desafio.acme.core.domain.factory.ProductFactory;
import br.com.itau.desafio.acme.core.exception.InsuranceQuoteException;
import br.com.itau.desafio.acme.infra.http.request.CustomerRequest;
import br.com.itau.desafio.acme.infra.http.request.InsuranceQuoteRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class InsuranceQuoteControllerTest extends AcmeApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InsuranceQuoteRepository insuranceQuoteRepository;

    @MockBean
    private ProductGateway productGateway;

    @MockBean
    private OfferGateway offerGateway;

    @MockBean
    private InsuranceQuoteQueue insuranceQuoteQueue;

    @BeforeEach
    public void setUp() {
        when(productGateway.getProductById(any())).thenReturn(ProductFactory.createProdut());
        when(offerGateway.getOfferById(any())).thenReturn(OfferFactory.create());
        doNothing().when(insuranceQuoteQueue).publish(any());
    }

    @Test
    @DisplayName("Should receive insurance quote successfully")
    public void receveidInsuranceQuoteTest() throws Exception {

        var insuranceQuoteRequest = new InsuranceQuoteRequest(
                UUID.fromString("1b2da7cc-b367-4196-8a78-9cfeec21f587"),
                UUID.fromString("adc56d77-348c-4bf0-908f-22d402ee715c"),
                Category.HOME.name(),
                75.25,
                825000.00,
                Map.of("Incêndio", 250000.0, "Desastres naturais", 500000.0, "Responsabiliadade civil", 75000.0),
                Set.of("Encanador", "Eletricista", "Chaveiro 24h"),
                new CustomerRequest(
                        "22222222222",
                        "Fulano de Tal",
                        "NATURAL" ,
                        "MALE",
                        LocalDate.of(1980, 1, 1),
                        "fulanodetal@email.com",
                        "11999999999"
                )
        );

        var body = objectMapper.writeValueAsString(insuranceQuoteRequest);

        mockMvc.perform(post("/api/v1/insurance-quote")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @DisplayName("Should not receive insurance quote")
    public void receveidInsuranceQuoteExceptionTest() throws Exception {

        var insuranceQuoteRequest = new InsuranceQuoteRequest(
                UUID.fromString("1b2da7cc-b367-4196-8a78-9cfeec21f587"),
                UUID.fromString("adc56d77-348c-4bf0-908f-22d402ee715c"),
                Category.HOME.name(),
                75.25,
                925000.00,
                Map.of("Incêndio", 250000.0, "Desastres naturais", 500000.0, "Responsabiliadade civil", 75000.0),
                Set.of("Encanador", "Eletricista", "Chaveiro 24h"),
                new CustomerRequest(
                        "11111111111",
                        "Fulano de Tal",
                        "NATURAL" ,
                        "MALE",
                        LocalDate.of(1980, 1, 1),
                        "fulanodetal@email.com",
                        "11999999999"
                )
        );

        var body = objectMapper.writeValueAsString(insuranceQuoteRequest);

        mockMvc.perform(post("/api/v1/insurance-quote")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect( result -> Assertions.assertTrue(result.getResolvedException() instanceof InsuranceQuoteException))
                .andExpect( result -> Assertions.assertEquals("Total coverage amount is greater than the total monthly premium amount", result.getResolvedException().getMessage()));
    }

    @Test
    @DisplayName("Should get insurance quote")
    public void getInsuranceQuoteTest() throws Exception {

        var insuranceQuote = InsuranceQuoteFactory.createInsuranceQuote();
        insuranceQuoteRepository.save(insuranceQuote);

        mockMvc.perform(get("/api/v1/insurance-quote/{id}", insuranceQuote.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.product_id").value(insuranceQuote.getProductId().toString()))
                .andExpect(jsonPath("$.offer_id").value(insuranceQuote.getOfferId().toString()))
                .andExpect(jsonPath("$.category").value(insuranceQuote.getCategory().name()));
    }

}
