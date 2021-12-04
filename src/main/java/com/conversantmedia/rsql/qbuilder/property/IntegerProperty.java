package com.conversantmedia.rsql.qbuilder.property;

import com.conversantmedia.rsql.qbuilder.Combiner;
import com.conversantmedia.rsql.qbuilder.QBuilder;
import com.conversantmedia.rsql.qbuilder.expression.QueryFragment;
import com.conversantmedia.rsql.qbuilder.property.virtual.NumberProperty;

import java.util.function.Supplier;

/**
 * Integer property
 * @param <T>
 */
public class IntegerProperty<T extends QBuilder<T>> extends NumberProperty<T,Integer> {

    public IntegerProperty(String name, Supplier<Combiner<T>> combinerSupplier, QueryFragment root) {
        super(name, combinerSupplier, root);
    }
}
