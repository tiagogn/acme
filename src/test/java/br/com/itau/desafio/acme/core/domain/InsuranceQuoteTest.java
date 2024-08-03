package br.com.itau.desafio.acme.core.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

public class InsuranceQuoteTest {

    @Test
    @DisplayName("Should validate sum of coverage")
    public void validateSumOfCoverage() {
        InsuranceQuote insuranceQuote = new InsuranceQuote();
        insuranceQuote.setCoverages(Set.of(new Coverage("coverage1", new BigDecimal("100")), new Coverage("coverage2", new BigDecimal("200"))));
        Assertions.assertEquals(new BigDecimal("300"), insuranceQuote.getTotalSumOfCoverage());
    }
}
