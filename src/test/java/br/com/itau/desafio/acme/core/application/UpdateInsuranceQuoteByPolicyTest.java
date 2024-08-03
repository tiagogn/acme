package br.com.itau.desafio.acme.core.application;

import br.com.itau.desafio.acme.AcmeApplicationTests;
import br.com.itau.desafio.acme.core.application.repository.InsuranceQuoteRepository;
import br.com.itau.desafio.acme.core.domain.InsuranceQuote;
import br.com.itau.desafio.acme.core.domain.factory.InsuranceQuoteFactory;
import br.com.itau.desafio.acme.core.dto.InsurancePolicyIssued;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UpdateInsuranceQuoteByPolicyTest extends AcmeApplicationTests {

    @Autowired
    private InsuranceQuoteRepository insuranceQuoteRepository;

    @Autowired
    private UpdateInsuranceQuoteByPolice updateInsuranceQuoteByPolice;

    @Test
    @DisplayName("Should update insurance quote with policy id")
    public void updatePolicyTest() {
        InsuranceQuote insuranceQuote = InsuranceQuoteFactory.createInsuranceQuote();
        insuranceQuoteRepository.save(insuranceQuote);

        InsurancePolicyIssued insurancePolicyIssued = new InsurancePolicyIssued(insuranceQuote.getId(), 123456);

        updateInsuranceQuoteByPolice.execute(insurancePolicyIssued);

        Optional<InsuranceQuote> insuranceQuoteOptional = insuranceQuoteRepository.findById(insuranceQuote.getId());

        Assertions.assertTrue(insuranceQuoteOptional.isPresent());
        Assertions.assertEquals(123456, insuranceQuoteOptional.get().getInsurancePolicyId());
        Assertions.assertEquals(insuranceQuote.getId(), insuranceQuoteOptional.get().getId());
    }

}
