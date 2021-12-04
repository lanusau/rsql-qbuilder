package com.conversantmedia.rsql.qbuilder.property;

import com.conversantmedia.rsql.qbuilder.Combiner;
import com.conversantmedia.rsql.qbuilder.QBuilder;
import com.conversantmedia.rsql.qbuilder.expression.QueryFragment;
import com.conversantmedia.rsql.qbuilder.property.virtual.NumberProperty;

import java.util.function.Supplier;

/**
 * Long property
 * @param <T>
 */
public class LongProperty<T extends QBuilder<T>> extends NumberProperty<T,Long> {

    public LongProperty(String name, Supplier<Combiner<T>> combinerSupplier, QueryFragment root) {
        super(name, combinerSupplier, root);
    }
}
