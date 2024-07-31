package br.com.itau.desafio.acme.core.domain;

import br.com.itau.desafio.acme.core.exception.InsuranceQuoteException;

import java.math.BigDecimal;
import java.util.Set;

public class ValidateInsuranceQuoteService {

    public void validate(InsuranceQuote insuranceQuote, Product product, Offer offer){
        validateProduct(product);
        validateOffer(offer);
        validateInsuranceQuote(insuranceQuote);
    }

    private void validateProduct(Product product) {
        if (product == null) {
            throw new InsuranceQuoteException("Product not found");
        }
        if (!product.isActive()){
            throw new InsuranceQuoteException("Product is not active");
        }
    }

    private void validateOffer(Offer offer) {
        if (offer == null) {
            throw new InsuranceQuoteException("Offer not found");
        }
        if (!offer.isActive()){
            throw new InsuranceQuoteException("Offer is not active");
        }
    }

    private void validateInsuranceQuote(InsuranceQuote insuranceQuote) {
        this.validateCoverares(insuranceQuote, insuranceQuote.getOffer());
        this.validateAssistance(insuranceQuote, insuranceQuote.getOffer());
        this.validateTotalMonthlyPremiumAmount(insuranceQuote, insuranceQuote.getOffer());
        this.validateTotalCoverageAmount(insuranceQuote);
    }

    private void validateCoverares(InsuranceQuote insuranceQuote, Offer offer){
        Set<Coverage> coveragesOffer = offer.getCoverages();
        Set<Coverage> coveragesInsuranceQuote = insuranceQuote.getCoverages();

        for (Coverage coverageInsuranceQuote : coveragesInsuranceQuote) {
            Coverage coverageOffer = coveragesOffer.stream().filter(coverage -> coverage.getId().equals(coverageInsuranceQuote.getId()))
                    .findFirst().orElseThrow(() -> new InsuranceQuoteException("Coverage not found in offer"));
            if (coverageOffer.getValue().compareTo(coverageInsuranceQuote.getValue()) < 0) {
                throw new InsuranceQuoteException("Coverage value is greater than the offer");
            }
        }
    }

    private void validateAssistance(InsuranceQuote insuranceQuote, Offer offer){
        Set<Assistance> assistancesOffer = offer.getAssistances();
        Set<Assistance> assistancesInsuranceQuote = insuranceQuote.getAssistances();

        if (!assistancesInsuranceQuote.containsAll(assistancesOffer)) {
            throw new InsuranceQuoteException("Assistance not found in offer");
        }
    }

    private void validateTotalMonthlyPremiumAmount(InsuranceQuote insuranceQuote, Offer offer){
        if (insuranceQuote.getTotalMonthlyPremiumAmount().compareTo(offer.getMonthlyPremiumAmount().getMaxAmount()) < 1
        && insuranceQuote.getTotalMonthlyPremiumAmount().compareTo(offer.getMonthlyPremiumAmount().getMinAmount()) > -1) {
            throw new InsuranceQuoteException("Total monthly premium amount is different from the offer");
        }
    }

    private void validateTotalCoverageAmount(InsuranceQuote insuranceQuote){
        var totalCoverageAmount = insuranceQuote.getCoverages().stream().map(Coverage::getValue)
                .map(BigDecimal::doubleValue).reduce(Double::sum).orElse(0.0);
        if (totalCoverageAmount > insuranceQuote.getTotalMonthlyPremiumAmount().doubleValue()) {
            throw new InsuranceQuoteException("Total coverage amount is greater than the total monthly premium amount");
        }
    }
}
