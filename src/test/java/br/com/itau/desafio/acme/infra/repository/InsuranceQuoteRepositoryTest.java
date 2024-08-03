package br.com.itau.desafio.acme.infra.repository;

import br.com.itau.desafio.acme.AcmeApplicationTests;
import br.com.itau.desafio.acme.core.application.repository.InsuranceQuoteRepository;
import br.com.itau.desafio.acme.core.domain.factory.InsuranceQuoteFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class InsuranceQuoteRepositoryTest extends AcmeApplicationTests {

    @Autowired
    private InsuranceQuoteRepository insuranceQuoteRepository;

    @Test
    @DisplayName("Should save insurance quote")
    public void saveTest() {

        var insuranceQuote = InsuranceQuoteFactory.createInsuranceQuote();

        insuranceQuoteRepository.save(insuranceQuote);

        var savedInsuranceQuote = insuranceQuoteRepository.findById(insuranceQuote.getId());

        Assertions.assertTrue(savedInsuranceQuote.isPresent());
        Assertions.assertEquals(insuranceQuote.getProductId(), savedInsuranceQuote.get().getProductId());
        Assertions.assertEquals(insuranceQuote.getOfferId(), savedInsuranceQuote.get().getOfferId());
        Assertions.assertEquals(insuranceQuote.getCustomer(), savedInsuranceQuote.get().getCustomer());
        Assertions.assertEquals(insuranceQuote.getTotalCoverageAmount().doubleValue(), savedInsuranceQuote.get().getTotalCoverageAmount().doubleValue());
        Assertions.assertEquals(insuranceQuote.getTotalMonthlyPremiumAmount().doubleValue(), savedInsuranceQuote.get().getTotalMonthlyPremiumAmount().doubleValue());
    }

    @Test
    @DisplayName("Should find insurance quote by id")
    public void findByIdTest() {

        var insuranceQuote = InsuranceQuoteFactory.createInsuranceQuote();

        insuranceQuoteRepository.save(insuranceQuote);

        var savedInsuranceQuote = insuranceQuoteRepository.findById(insuranceQuote.getId());

        Assertions.assertTrue(savedInsuranceQuote.isPresent());
        Assertions.assertEquals(insuranceQuote.getProductId(), savedInsuranceQuote.get().getProductId());
        Assertions.assertEquals(insuranceQuote.getOfferId(), savedInsuranceQuote.get().getOfferId());
        Assertions.assertEquals(insuranceQuote.getCustomer(), savedInsuranceQuote.get().getCustomer());
        Assertions.assertEquals(insuranceQuote.getTotalCoverageAmount().doubleValue(), savedInsuranceQuote.get().getTotalCoverageAmount().doubleValue());
        Assertions.assertEquals(insuranceQuote.getTotalMonthlyPremiumAmount().doubleValue(), savedInsuranceQuote.get().getTotalMonthlyPremiumAmount().doubleValue());
    }

    @Test
    @DisplayName("Should find insurance quote by insurance policy id")
    public void findByInsurancePolicyIdTest() {

        var insuranceQuote = InsuranceQuoteFactory.createInsuranceQuote();
        insuranceQuote.setInsurancePolicyId(123456);

        insuranceQuoteRepository.save(insuranceQuote);

        var savedInsuranceQuote = insuranceQuoteRepository.findByInsurancePolicyId(insuranceQuote.getInsurancePolicyId());

        Assertions.assertTrue(savedInsuranceQuote.isPresent());
        Assertions.assertEquals(insuranceQuote.getProductId(), savedInsuranceQuote.get().getProductId());
        Assertions.assertEquals(insuranceQuote.getOfferId(), savedInsuranceQuote.get().getOfferId());
        Assertions.assertEquals(insuranceQuote.getCustomer(), savedInsuranceQuote.get().getCustomer());
        Assertions.assertEquals(insuranceQuote.getTotalCoverageAmount().doubleValue(), savedInsuranceQuote.get().getTotalCoverageAmount().doubleValue());
        Assertions.assertEquals(insuranceQuote.getTotalMonthlyPremiumAmount().doubleValue(), savedInsuranceQuote.get().getTotalMonthlyPremiumAmount().doubleValue());
    }

    @Test
    @DisplayName("Should not find insurance quote by insurance policy id")
    public void findByInsurancePolicyIdNotFoundTest() {

        var insuranceQuote = InsuranceQuoteFactory.createInsuranceQuote();
        insuranceQuote.setInsurancePolicyId(123456);

        insuranceQuoteRepository.save(insuranceQuote);

        var savedInsuranceQuote = insuranceQuoteRepository.findByInsurancePolicyId(654321);

        Assertions.assertTrue(savedInsuranceQuote.isEmpty());
    }

    @Test
    @DisplayName("Should check if insurance quote exists by product id, offer id and customer document")
    public void existsByProductIdAndOfferIdAndCustomerDocumentTest() {

        var insuranceQuote = InsuranceQuoteFactory.createInsuranceQuote();

        insuranceQuoteRepository.save(insuranceQuote);

        var exists = insuranceQuoteRepository.existsByProductIdAndOfferIdAndCustomerDocument(insuranceQuote.getProductId(),
                insuranceQuote.getOfferId(), insuranceQuote.getCustomer().getDocumentNumber());

        Assertions.assertTrue(exists);
    }
}
