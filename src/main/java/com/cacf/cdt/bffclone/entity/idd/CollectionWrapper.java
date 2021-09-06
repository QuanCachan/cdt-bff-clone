package com.cacf.cdt.bffclone.entity.idd;

import lombok.experimental.Delegate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Avoid ERROR org.hibernate.metamodel.internal.MetadataContext - HHH015007:
 * Illegal argument on static metamodel field injection :
 * expected type :  org.hibernate.metamodel.model.domain.internal.SingularAttributeImpl;
 * encountered type : javax.persistence.metamodel.ListAttribute
 *
 * https://github.com/hibernate/hibernate-orm/pull/3254
 * @param <T>
 */
public class CollectionWrapper<T> implements Collection<T> {
    @Delegate
    private final Collection<T> internal;

    public static <T> CollectionWrapper<T> of(T... documents) {
        return new CollectionWrapper<>(Arrays.asList(documents));
    }

    public CollectionWrapper() {
        this.internal = new ArrayList<>();
    }

    public CollectionWrapper(Collection<T> collection) {
        this.internal = collection;
    }
}
