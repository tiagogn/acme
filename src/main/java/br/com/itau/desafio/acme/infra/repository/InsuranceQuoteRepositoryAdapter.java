package br.com.itau.desafio.acme.infra.repository;

import br.com.itau.desafio.acme.core.application.repository.InsuranceQuoteRepository;
import br.com.itau.desafio.acme.core.domain.*;
import br.com.itau.desafio.acme.infra.entity.CoverageEntity;
import br.com.itau.desafio.acme.infra.entity.CustomerEntity;
import br.com.itau.desafio.acme.infra.entity.InsuranceQuoteEntity;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Qualifier("insuranceQuoteRepository")
@Transactional
public class InsuranceQuoteRepositoryAdapter implements InsuranceQuoteRepository {

    private final EntityManager entityManager;

    public InsuranceQuoteRepositoryAdapter(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(InsuranceQuote insuranceQuote) {

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

        entityManager.persist(customerEntity);

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

        entityManager.persist(insuranceQuoteEntity);
    }

    @Override
    public Optional<InsuranceQuote> findById(Long id) {
        var insuranceQuoteEntity = entityManager.createQuery("SELECT i FROM InsuranceQuoteEntity i WHERE i.id = :id", InsuranceQuoteEntity.class)
                .setParameter("id", id)
                .getResultStream()
                .findAny();

        if (insuranceQuoteEntity.isPresent())
            return new InsuranceQuoteEntityMapper().mapper(insuranceQuoteEntity.get());
        else
            return Optional.empty();
    }

    @Override
    public Optional<InsuranceQuote> findByInsurancePolicyId(UUID insurancePolicyId) {
        var insuranceQuoteEntity = entityManager.createQuery("SELECT i FROM InsuranceQuoteEntity i WHERE i.insurancePolicyId = :insurancePolicyId", InsuranceQuoteEntity.class)
                .setParameter("insurancePolicyId", insurancePolicyId)
                .getResultStream()
                .findAny();

        if (insuranceQuoteEntity.isPresent())
            return new InsuranceQuoteEntityMapper().mapper(insuranceQuoteEntity.get());
        else
            return Optional.empty();
    }
}

class InsuranceQuoteEntityMapper {

    public Optional<InsuranceQuote> mapper(InsuranceQuoteEntity insuranceQuoteEntity){
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
}