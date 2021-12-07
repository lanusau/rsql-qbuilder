package com.conversantmedia.mpub.rsql.qbuilder.property;

import com.conversantmedia.mpub.rsql.qbuilder.Combiner;
import com.conversantmedia.mpub.rsql.qbuilder.QBuilder;
import com.conversantmedia.mpub.rsql.qbuilder.expression.QueryFragment;
import com.conversantmedia.mpub.rsql.qbuilder.property.virtual.ListableProperty;

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
