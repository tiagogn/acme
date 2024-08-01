package br.com.itau.desafio.acme.infra.http.response;

import br.com.itau.desafio.acme.core.domain.Assistance;
import br.com.itau.desafio.acme.core.domain.InsuranceQuote;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public record InsuranceQuoteResponse(
        Integer id,
        @JsonProperty("insurance_policy_id")
        Integer insurancePolicyId,
        @JsonProperty("product_id")
        String productId,
        @JsonProperty("offer_id")
        String offerId,
        String category,
        @JsonProperty("created_at")
        String createdAt,
        @JsonProperty("updated_at")
        String updatedAt,
        @JsonProperty("total_monthly_premium_amount")
        String totalMonthlyPremiumAmount,
        @JsonProperty("total_coverage_amount")
        String totalCoverageAmount,
        Set<Map<String, Double>> coverages,
        Set<String> assistances,
        CustomerResponse customer
) {
    public static InsuranceQuoteResponse fromDomain(InsuranceQuote insuranceQuote) {
        return new InsuranceQuoteResponse(
                insuranceQuote.getId(),
                insuranceQuote.getInsurancePolicyId(),
                insuranceQuote.getProductId().toString(),
                insuranceQuote.getOfferId().toString(),
                insuranceQuote.getCategory().name(),
                insuranceQuote.getCreatedAt().toString(),
                insuranceQuote.getUpdatedAt().toString(),
                insuranceQuote.getTotalMonthlyPremiumAmount().toString(),
                insuranceQuote.getTotalCoverageAmount().toString(),
                insuranceQuote.getCoverages().stream().map(c -> Map.of(c.getName(), c.getValue().doubleValue())).collect(Collectors.toSet()),
                insuranceQuote.getAssistances().stream().map(Assistance::getName).collect(Collectors.toSet()),
                CustomerResponse.fromDomain(insuranceQuote.getCustomer())
        );
    }
}

