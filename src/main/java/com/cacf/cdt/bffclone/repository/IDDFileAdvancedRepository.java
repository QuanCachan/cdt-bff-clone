package com.cacf.cdt.bffclone.repository;

import com.cacf.cdt.bffclone.entity.idd.IDDFile;
import com.cacf.cdt.bffclone.entity.idd.IDDFile_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.Optional;

@Repository
public class IDDFileAdvancedRepository extends SimpleJpaRepository<IDDFile, String> {
    private final EntityManager entityManager;

    public IDDFileAdvancedRepository(EntityManager em) {
        super(IDDFile.class, em);
        this.entityManager = em;
    }

    public BigDecimal sumAmount(Specification<IDDFile> specification) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> query = criteriaBuilder.createQuery(BigDecimal.class);

        Root<IDDFile> root = query.from(getDomainClass());
        query.select(criteriaBuilder.sum(root.get(IDDFile_.amount)));
        Optional.ofNullable(specification)
                .map(spec -> spec.toPredicate(root, query, criteriaBuilder))
                .ifPresent(query::where);

        TypedQuery<BigDecimal> typedQuery = entityManager.createQuery(query);
        return typedQuery.getSingleResult();
    }
}
