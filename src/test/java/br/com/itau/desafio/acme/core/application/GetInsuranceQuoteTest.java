package br.com.itau.desafio.acme.core.application;

import br.com.itau.desafio.acme.AcmeApplicationTests;
import br.com.itau.desafio.acme.core.application.repository.InsuranceQuoteRepository;
import br.com.itau.desafio.acme.core.domain.InsuranceQuote;
import br.com.itau.desafio.acme.core.domain.InsuranceQuoteFactory;
import br.com.itau.desafio.acme.core.dto.InsurancePolicyIssued;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GetInsuranceQuoteTest extends AcmeApplicationTests {

    @Autowired
    private InsuranceQuoteRepository insuranceQuoteRepository;

    @Autowired
    private GetInsuranceQuote getInsuranceQuote;

    @Autowired
    private UpdateInsuranceQuoteByPolice updateInsuranceQuoteByPolice;

    @Test
    @DisplayName("Should get insurance quote by id")
    public void getInsuranceQuoteByIdTest() {
        InsuranceQuote insuranceQuote = InsuranceQuoteFactory.createInsuranceQuote();
        insuranceQuoteRepository.save(insuranceQuote);

        InsuranceQuote getInsuranceQuoteById = getInsuranceQuote.getById(insuranceQuote.getId());

        Assertions.assertNotNull(getInsuranceQuoteById);
        Assertions.assertEquals(insuranceQuote.getId(), getInsuranceQuoteById.getId());
        Assertions.assertEquals(insuranceQuote.getAssistances(), getInsuranceQuoteById.getAssistances());
        Assertions.assertEquals(insuranceQuote.getAssistances(), getInsuranceQuoteById.getAssistances());
    }

    @Test
    @DisplayName("Should get insurance quote by id")
    public void getInsuranceQuoteByPolicyIdTest() {
        InsuranceQuote insuranceQuote = InsuranceQuoteFactory.createInsuranceQuote();
        insuranceQuoteRepository.save(insuranceQuote);

        var insurancePolicyIssued = new InsurancePolicyIssued(insuranceQuote.getId(), 123456);

        updateInsuranceQuoteByPolice.execute(insurancePolicyIssued);

        InsuranceQuote getInsuranceQuoteById = getInsuranceQuote.getByInsuranceId(insurancePolicyIssued.insurancePolicyId());

        Assertions.assertNotNull(getInsuranceQuoteById);
        Assertions.assertEquals(insuranceQuote.getId(), getInsuranceQuoteById.getId());
        Assertions.assertEquals(insuranceQuote.getAssistances(), getInsuranceQuoteById.getAssistances());
        Assertions.assertEquals(insuranceQuote.getAssistances(), getInsuranceQuoteById.getAssistances());
    }
}
