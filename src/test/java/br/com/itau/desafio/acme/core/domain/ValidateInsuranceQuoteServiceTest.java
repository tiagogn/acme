package br.com.itau.desafio.acme.core.domain;

import br.com.itau.desafio.acme.AcmeApplicationTests;
import br.com.itau.desafio.acme.core.domain.factory.InsuranceQuoteFactory;
import br.com.itau.desafio.acme.core.domain.factory.OfferFactory;
import br.com.itau.desafio.acme.core.domain.factory.ProductFactory;
import br.com.itau.desafio.acme.core.exception.InsuranceQuoteException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

public class ValidateInsuranceQuoteServiceTest extends AcmeApplicationTests {

    @Test
    @DisplayName("Should validate product is null")
    public void validateProductIsNull() {
        var validateInsuranceQuoteService = new ValidateInsuranceQuoteService();
        Assertions.assertThrows(InsuranceQuoteException.class, () -> {
            validateInsuranceQuoteService.validate(InsuranceQuoteFactory.createInsuranceQuote(), null, null);
        });
    }

    @Test
    @DisplayName("Should validate product is not active")
    public void validateProductIsNotActive() {
        var validateInsuranceQuoteService = new ValidateInsuranceQuoteService();
        Product produt = ProductFactory.createProdut(false);
        Offer offer = OfferFactory.create();
        Assertions.assertThrows(InsuranceQuoteException.class, () -> {
            validateInsuranceQuoteService.validate(InsuranceQuoteFactory.createInsuranceQuote(), null, null);
        });
    }

    @Test
    @DisplayName("Should validate product is not null and active")
    public void validateProductActive() {
        var validateInsuranceQuoteService = new ValidateInsuranceQuoteService();
        Product produt = ProductFactory.createProdut(true);
        Offer offer = OfferFactory.create();
        Assertions.assertDoesNotThrow(
                () -> validateInsuranceQuoteService.validate(InsuranceQuoteFactory.createInsuranceQuote(), produt, offer)
        );
    }

    @Test
    @DisplayName("Should validate offer is null")
    public void validateOfferIsNull() {
        var validateInsuranceQuoteService = new ValidateInsuranceQuoteService();
        Assertions.assertThrows(InsuranceQuoteException.class, () -> {
            validateInsuranceQuoteService.validate(InsuranceQuoteFactory.createInsuranceQuote(), ProductFactory.createProdut(true), null);
        });
    }

    @Test
    @DisplayName("Should validate offer is not active")
    public void validateOfferIsNotActive() {
        var validateInsuranceQuoteService = new ValidateInsuranceQuoteService();
        Product produt = ProductFactory.createProdut(true);
        Offer offer = OfferFactory.create(false);
        Assertions.assertThrows(InsuranceQuoteException.class, () -> {
            validateInsuranceQuoteService.validate(InsuranceQuoteFactory.createInsuranceQuote(), produt, offer);
        });
    }

    @Test
    @DisplayName("Should validate offer is not null and active")
    public void validateOfferActive() {
        var validateInsuranceQuoteService = new ValidateInsuranceQuoteService();
        Product produt = ProductFactory.createProdut(true);
        Offer offer = OfferFactory.create(true);
        Assertions.assertDoesNotThrow(
                () -> validateInsuranceQuoteService.validate(InsuranceQuoteFactory.createInsuranceQuote(), produt, offer)
        );
    }

    @Test
    @DisplayName("Should validate coverages values")
    public void validateCoverages() {
        var validateInsuranceQuoteService = new ValidateInsuranceQuoteService();
        Product produt = ProductFactory.createProdut(true);
        Offer offer = OfferFactory.create(true);
        InsuranceQuote insuranceQuote = InsuranceQuoteFactory.createInsuranceQuote();
        insuranceQuote.setCoverages(Set.of(new Coverage("Incêndio", BigDecimal.valueOf(1250000.00)),
                new Coverage("Desastres naturais", BigDecimal.valueOf(1500000.00)),
                new Coverage("Responsabiliadade civil", BigDecimal.valueOf(175000.00))));
        Assertions.assertThrows(InsuranceQuoteException.class, () -> {
                validateInsuranceQuoteService.validate(insuranceQuote, produt, offer);
        });
    }

    @Test
    @DisplayName("Should validate assistances valid in Offer")
    public void validateAssistances() {
        var validateInsuranceQuoteService = new ValidateInsuranceQuoteService();
        Product produt = ProductFactory.createProdut(true);
        Offer offer = OfferFactory.create(true);
        InsuranceQuote insuranceQuote = InsuranceQuoteFactory.createInsuranceQuote();
        insuranceQuote.setAssistances(Set.of(new Assistance("Encanador"),
                new Assistance("Eletricista"),
                new Assistance("Quincho 24h")));
        Assertions.assertThrows(InsuranceQuoteException.class, () -> {
            validateInsuranceQuoteService.validate(insuranceQuote, produt, offer);
        });
    }

    @Test
    @DisplayName("Should validate total monthly premium amount less than min amount Offer")
    public void validateTotalMonthlyPremiumAmountLt() {
        var validateInsuranceQuoteService = new ValidateInsuranceQuoteService();
        Product produt = ProductFactory.createProdut(true);
        Offer offer = OfferFactory.create(true);
        InsuranceQuote insuranceQuote = InsuranceQuoteFactory.createInsuranceQuote();
        insuranceQuote.setTotalMonthlyPremiumAmount(BigDecimal.valueOf(40.00));
        Assertions.assertThrows(InsuranceQuoteException.class, () -> {
            validateInsuranceQuoteService.validate(insuranceQuote, produt, offer);
        });
    }

    @Test
    @DisplayName("Should validate total monthly premium amount greater than max amount Offer")
    public void validateTotalMonthlyPremiumAmountGt() {
        var validateInsuranceQuoteService = new ValidateInsuranceQuoteService();
        Product produt = ProductFactory.createProdut(true);
        Offer offer = OfferFactory.create(true);
        InsuranceQuote insuranceQuote = InsuranceQuoteFactory.createInsuranceQuote();
        insuranceQuote.setTotalMonthlyPremiumAmount(BigDecimal.valueOf(130.00));
        Assertions.assertThrows(InsuranceQuoteException.class, () -> {
            validateInsuranceQuoteService.validate(insuranceQuote, produt, offer);
        });
    }

    @Test
    @DisplayName("Should validate total coverage amount")
    public void validateTotalCoverageAmount() {
        var validateInsuranceQuoteService = new ValidateInsuranceQuoteService();
        Product produt = ProductFactory.createProdut(true);
        Offer offer = OfferFactory.create(true);
        InsuranceQuote insuranceQuote = InsuranceQuoteFactory.createInsuranceQuote();
        insuranceQuote.setTotalCoverageAmount(BigDecimal.valueOf(1000.00));
        Assertions.assertThrows(InsuranceQuoteException.class, () -> {
            validateInsuranceQuoteService.validate(insuranceQuote, produt, offer);
        });
    }
}
