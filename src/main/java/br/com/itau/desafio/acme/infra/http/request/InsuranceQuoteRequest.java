package br.com.itau.desafio.acme.infra.http.request;

import br.com.itau.desafio.acme.core.domain.Assistance;
import br.com.itau.desafio.acme.core.domain.Category;
import br.com.itau.desafio.acme.core.domain.Coverage;
import br.com.itau.desafio.acme.core.domain.InsuranceQuote;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record InsuranceQuoteRequest(
        UUID product_id,
        UUID offer_id,
        String category,
        double total_monthly_premium_amount,
        double total_coverage_amount,
        @JsonProperty("coverages")
        Map<String, Double> coverages,
        @JsonProperty("assistances")
        Set<String> assistances,
        CustomerRequest customer
){
        public InsuranceQuote toDomain() {
                var insuranceQuote = new InsuranceQuote();
                insuranceQuote.setProductId(this.product_id);
                insuranceQuote.setOfferId(this.offer_id);
                insuranceQuote.setCategory(Category.valueOf(this.category));
                insuranceQuote.setTotalMonthlyPremiumAmount(BigDecimal.valueOf(this.total_monthly_premium_amount));
                insuranceQuote.setTotalCoverageAmount(BigDecimal.valueOf(this.total_coverage_amount));
                insuranceQuote.setCoverages(this.coverages.entrySet().stream()
                        .map(entry -> new Coverage(entry.getKey(), BigDecimal.valueOf(entry.getValue())))
                        .collect(Collectors.toSet()));
                insuranceQuote.setAssistances(this.assistances.stream()
                        .map(Assistance::new)
                        .collect(Collectors.toSet()));
                insuranceQuote.setCustomer(this.customer.toDomain());
                return insuranceQuote;
        }
}


