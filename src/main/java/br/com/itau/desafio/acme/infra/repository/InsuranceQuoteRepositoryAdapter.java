package br.com.itau.desafio.acme.infra.repository;

import br.com.itau.desafio.acme.core.application.repository.InsuranceQuoteRepository;
import br.com.itau.desafio.acme.core.domain.*;
import br.com.itau.desafio.acme.infra.entity.CoverageEntity;
import br.com.itau.desafio.acme.infra.entity.CustomerEntity;
import br.com.itau.desafio.acme.infra.entity.InsuranceQuoteEntity;
import br.com.itau.desafio.acme.infra.repository.mapper.InsuranceQuoteEntityMapper;
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

    private final InsuranceQuoteEntityMapper insuranceQuoteEntityMapper = new InsuranceQuoteEntityMapper();

    public InsuranceQuoteRepositoryAdapter(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(InsuranceQuote insuranceQuote) {
        entityManager.persist(insuranceQuoteEntityMapper.toEntity(insuranceQuote));
    }

    @Override
    public Optional<InsuranceQuote> findById(Long id) {
        var insuranceQuoteEntity = entityManager.createQuery("SELECT i FROM InsuranceQuoteEntity i WHERE i.id = :id", InsuranceQuoteEntity.class)
                .setParameter("id", id)
                .getResultStream()
                .findAny();

        if (insuranceQuoteEntity.isPresent())
            return insuranceQuoteEntityMapper.toDomain(insuranceQuoteEntity.get());
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
            return insuranceQuoteEntityMapper.toDomain(insuranceQuoteEntity.get());
        else
            return Optional.empty();
    }

    @Override
    public boolean existsByProductIdAndOfferIdAndCustomerDocument(UUID productId, UUID offerId, String documentNumber) {
        var insuranceQuoteEntity = entityManager.createQuery("SELECT i FROM InsuranceQuoteEntity i WHERE i.productId = :productId AND i.offerId = :offerId AND i.customerEntity.documentNumber = :documentNumber", InsuranceQuoteEntity.class)
                .setParameter("productId", productId)
                .setParameter("offerId", offerId)
                .setParameter("documentNumber", documentNumber)
                .getResultStream()
                .findAny();

        return insuranceQuoteEntity.isPresent();
    }
}
