package br.com.itau.desafio.acme.core.application;

import br.com.itau.desafio.acme.AcmeApplicationTests;
import br.com.itau.desafio.acme.core.application.gateway.OfferGateway;
import br.com.itau.desafio.acme.core.application.gateway.ProductGateway;
import br.com.itau.desafio.acme.core.application.queue.InsuranceQuoteQueue;
import br.com.itau.desafio.acme.core.application.repository.InsuranceQuoteRepository;
import br.com.itau.desafio.acme.core.domain.InsuranceQuoteFactory;
import br.com.itau.desafio.acme.core.domain.OfferFactory;
import br.com.itau.desafio.acme.core.domain.ProductFactory;
import br.com.itau.desafio.acme.core.exception.InsuranceQuoteException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReceivedInsuranceQuoteTest extends AcmeApplicationTests {

    @Autowired
    private ReceivedInsuranceQuote receivedInsuranceQuote;

    @MockBean
    private ProductGateway productGateway;

    @MockBean
    private OfferGateway offerGateway;

    @MockBean
    private InsuranceQuoteQueue insuranceQuoteQueue;

    @Autowired
    private InsuranceQuoteRepository insuranceQuoteRepository;

    @Test
    @DisplayName("Should validate received insurance quote successfully")
    public void validateReceivedInsuranceQuote() {

        when(productGateway.getProductById(any())).thenReturn(ProductFactory.createProdut());
        when(offerGateway.getOfferById(any())).thenReturn(OfferFactory.create());
        doNothing().when(insuranceQuoteQueue).publish(any());

        var insuranceQuote = InsuranceQuoteFactory.createInsuranceQuote();

        receivedInsuranceQuote.execute(insuranceQuote);

        verify(productGateway, times(1)).getProductById(any());
        verify(offerGateway, times(1)).getOfferById(any());
        verify(insuranceQuoteQueue, times(1)).publish(any());

        Assertions.assertNotNull(insuranceQuote.getId());
        Assertions.assertTrue(insuranceQuote.getId() > 0);
        Assertions.assertEquals(0, insuranceQuote.getInsurancePolicyId());
    }

    @Test
    @DisplayName("Should throw exception when insurance quote already exists")
    public void shouldThrowExceptionWhenInsuranceQuoteAlreadyExists() {

        var insuranceQuote = InsuranceQuoteFactory.createInsuranceQuote();
        insuranceQuoteRepository.save(insuranceQuote);

        Assertions.assertThrows(InsuranceQuoteException.class, () -> receivedInsuranceQuote.execute(insuranceQuote));
    }
}
