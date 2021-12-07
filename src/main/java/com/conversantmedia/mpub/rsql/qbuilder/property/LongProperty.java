package com.conversantmedia.mpub.rsql.qbuilder.property;

import com.conversantmedia.mpub.rsql.qbuilder.Combiner;
import com.conversantmedia.mpub.rsql.qbuilder.QBuilder;
import com.conversantmedia.mpub.rsql.qbuilder.expression.QueryFragment;
import com.conversantmedia.mpub.rsql.qbuilder.property.virtual.NumberProperty;

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
