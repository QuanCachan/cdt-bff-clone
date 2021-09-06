package com.cacf.cdt.bffclone.service;

import com.cacf.cdt.bffclone.dto.idd.filter.IDDFileFilterDTO;
import com.cacf.cdt.bffclone.entity.cdt.task.CDTTaskPriority;
import com.cacf.cdt.bffclone.entity.cdt.task.CDTTaskType;
import com.cacf.cdt.bffclone.entity.cdt.task.CDTTask_;
import com.cacf.cdt.bffclone.entity.cdt.user.CDTUser_;
import com.cacf.cdt.bffclone.entity.idd.IDDEgdCode;
import com.cacf.cdt.bffclone.entity.idd.IDDFile;
import com.cacf.cdt.bffclone.entity.idd.IDDFile_;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class IDDFileSpecification implements Specification<IDDFile> {

    private final IDDFileFilterDTO filter;
    private Root<IDDFile> root;
    private CriteriaBuilder builder;

    @Override
    public Predicate toPredicate(Root<IDDFile> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (filter == null) {
            return null;
        }
        this.root = root;
        this.builder = builder;

        val predicates = Stream.of(
                        hasAgencies(),
                        hasAdvisorNumbers(),
                        hasProductTypes(),
                        hasEnteredDate(),
                        hasAmount(),
                        hasSubscriptionMode(),
                        hasEgdCodes(),
                        hasDlgNumbers(),
                        hasEnteredByAdvisorNumbers(),
                        hasTaskTypes(),
                        hasAffectedTo(),
                        hasTaskPriorities()
                )
                .filter(Objects::nonNull)
                .toArray(Predicate[]::new);
        if (predicates.length == 0) {
            return null;
        } else {
            return builder.and(predicates);
        }
    }

    private <E extends Enum<E>> Predicate isInEnum(Class<E> enumClass, Collection<String> collection, Path<E> path) {
        return isIn(
                Optional.ofNullable(collection)
                        .orElse(Collections.emptySet())
                        .stream()
                        .map(code -> EnumUtils.getEnumIgnoreCase(enumClass, code))
                        .collect(Collectors.toSet())
                , path
        );
    }

    private <T> Predicate isIn(Collection<T> collection, Path<T> path) {
        return Optional.ofNullable(collection)
                .filter(java.util.function.Predicate.not(Collection::isEmpty))
                .map(path::in)
                .orElse(null);
    }

    private Predicate hasProductTypes() {
        return isIn(filter.getProductTypes(), root.get(IDDFile_.productType));
    }

    private Predicate hasAgencies() {
        return isIn(filter.getAgencies(), root.get(IDDFile_.agency));
    }

    private Predicate hasAdvisorNumbers() {
        return isIn(filter.getAdvisors(), root.get(IDDFile_.advisor).get(CDTUser_.number));
    }

    private Predicate hasEnteredDate() {
        if (filter.getEnteredDate() == null) {
            return null;
        }
        val range = filter.getEnteredDate();
        val predicateFrom = Optional.ofNullable(range.getFrom())
                .map(from -> builder.greaterThanOrEqualTo(root.get(IDDFile_.enteredDate), from));
        val predicateTo = Optional.ofNullable(range.getTo())
                .map(to -> builder.lessThanOrEqualTo(root.get(IDDFile_.enteredDate), to));

        val predicates = Stream.of(predicateFrom, predicateTo)
                .filter(Optional::isPresent).map(Optional::get)
                .toArray(Predicate[]::new);
        if (predicates.length == 0) {
            return null;
        } else {
            return builder.and(predicates);
        }
    }

    private Predicate hasAmount() {
        if (filter.getAmount() == null) {
            return null;
        }
        val minMaxAmount = filter.getAmount();
        val predicateMin = Optional.ofNullable(minMaxAmount.getMin())
                .map(min -> builder.greaterThanOrEqualTo(root.get(IDDFile_.amount), min));
        val predicateMax = Optional.ofNullable(minMaxAmount.getMax())
                .map(max -> builder.lessThanOrEqualTo(root.get(IDDFile_.amount), max));

        val predicates = Stream.of(predicateMin, predicateMax)
                .filter(Optional::isPresent).map(Optional::get)
                .toArray(Predicate[]::new);
        if (predicates.length == 0) {
            return null;
        } else {
            return builder.and(predicates);
        }
    }

    private Predicate hasSubscriptionMode() {
        return isIn(filter.getSubscriptionModes(), root.get(IDDFile_.subscriptionMode));
    }

    private Predicate hasEgdCodes() {
        return isInEnum(IDDEgdCode.class, filter.getEgdCodes(), root.get(IDDFile_.egdCode));
    }

    private Predicate hasDlgNumbers() {
        return isIn(filter.getDlgNumbers(), root.get(IDDFile_.dlgNumber));
    }

    private Predicate hasEnteredByAdvisorNumbers() {
        return isIn(filter.getEnteredBy(), root.get(IDDFile_.enteredBy).get(CDTUser_.number));
    }

    private Predicate hasTaskTypes() {
        return isInEnum(CDTTaskType.class, filter.getTaskTypes(), root.get(IDDFile_.task).get(CDTTask_.type));
    }

    private Predicate hasAffectedTo() {
        return isIn(filter.getTaskAffectedTo(), root.get(IDDFile_.task).get(CDTTask_.affectedTo).get(CDTUser_.number));
    }

    private Predicate hasTaskPriorities() {
        return isInEnum(CDTTaskPriority.class, filter.getTaskPriorities(), root.get(IDDFile_.task).get(CDTTask_.priority));
    }
}
