package com.conversantmedia.rsql.qbuilder.property;

import com.conversantmedia.rsql.qbuilder.Combiner;
import com.conversantmedia.rsql.qbuilder.QBuilder;
import com.conversantmedia.rsql.qbuilder.expression.QueryFragment;
import com.conversantmedia.rsql.qbuilder.property.virtual.ListableProperty;

import java.util.function.Supplier;

/**
 * Enumeration property
 * @param <T>
 * @param <V>
 */
public class EnumProperty<T extends QBuilder<T>,V extends Enum<V>> extends ListableProperty<T,V> {

    public EnumProperty(String name, Supplier<Combiner<T>> combinerSupplier, QueryFragment root) {
        super(name, combinerSupplier, root);
    }
}
