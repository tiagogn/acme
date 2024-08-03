package br.com.itau.desafio.acme.infra.repository.mapper;

import br.com.itau.desafio.acme.core.domain.*;
import br.com.itau.desafio.acme.infra.entity.CoverageEntity;
import br.com.itau.desafio.acme.infra.entity.CustomerEntity;
import br.com.itau.desafio.acme.infra.entity.InsuranceQuoteEntity;

import java.util.Optional;
import java.util.stream.Collectors;

public class InsuranceQuoteEntityMapper {

    public Optional<InsuranceQuote> toDomain(InsuranceQuoteEntity insuranceQuoteEntity){
        var customer = insuranceQuoteEntity.getCustomerEntity();

        return Optional.of(new InsuranceQuote(
                insuranceQuoteEntity.getId(),
                insuranceQuoteEntity.getInsurancePolicyId(),
                insuranceQuoteEntity.getProductId(),
                insuranceQuoteEntity.getOfferId(),
                Category.valueOf(insuranceQuoteEntity.getCategory()),
                insuranceQuoteEntity.getCreatedAt(),
                insuranceQuoteEntity.getUpdatedAt(),
                insuranceQuoteEntity.getTotalMonthlyPremiumAmount(),
                insuranceQuoteEntity.getTotalCoverageAmount(),
                insuranceQuoteEntity.getCoverageEntities().stream().map(coverageEntity -> new Coverage(coverageEntity.getName(), coverageEntity.getValue())).collect(Collectors.toSet()),
                insuranceQuoteEntity.getAssistances().stream().map(Assistance::new).collect(Collectors.toSet()),
                new Customer(
                        customer.getDocumentNumber(),
                        customer.getName(),
                        customer.getType(),
                        Gender.valueOf(customer.getGender()),
                        customer.getDateOfBirth(),
                        customer.getEmail(),
                        customer.getPhoneNumber())
        ));
    }

    public InsuranceQuoteEntity toEntity(InsuranceQuote insuranceQuote){
        var insuranceQuoteEntity = new InsuranceQuoteEntity();
        var customer = insuranceQuote.getCustomer();

        var customerEntity = new CustomerEntity(
                customer.getDocumentNumber(),
                customer.getName(),
                customer.getType(),
                customer.getGender().name(),
                customer.getDateOfBirth(),
                customer.getEmail(),
                customer.getPhoneNumber()
        );

        if (insuranceQuote.getId() != null && insuranceQuote.getId() > 0)
            insuranceQuoteEntity.setId(insuranceQuote.getId());

        insuranceQuoteEntity.setInsurancePolicyId(insuranceQuote.getInsurancePolicyId());
        insuranceQuoteEntity.setProductId(insuranceQuote.getProductId());
        insuranceQuoteEntity.setOfferId(insuranceQuote.getOfferId());
        insuranceQuoteEntity.setCategory(insuranceQuote.getCategory().name());
        insuranceQuoteEntity.setTotalMonthlyPremiumAmount(insuranceQuote.getTotalMonthlyPremiumAmount());
        insuranceQuoteEntity.setTotalCoverageAmount(insuranceQuote.getTotalCoverageAmount());
        insuranceQuoteEntity.setAssistances(insuranceQuote.getAssistances()
                .stream()
                .map(Assistance::getName).collect(Collectors.toSet()));
        insuranceQuoteEntity.setCoverageEntities(insuranceQuote.getCoverages()
                .stream()
                .map(coverage -> new CoverageEntity(insuranceQuoteEntity, coverage.getName(), coverage.getValue()))
                .collect(Collectors.toSet()));

        insuranceQuoteEntity.setCustomerEntity(customerEntity);
        insuranceQuoteEntity.setCreatedAt(insuranceQuote.getCreatedAt());
        insuranceQuoteEntity.setUpdatedAt(insuranceQuote.getUpdatedAt());

        return insuranceQuoteEntity;
    }
}
