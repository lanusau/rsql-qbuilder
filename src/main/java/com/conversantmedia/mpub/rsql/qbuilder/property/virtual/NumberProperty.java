package com.conversantmedia.mpub.rsql.qbuilder.property.virtual;

import com.conversantmedia.mpub.rsql.qbuilder.Combiner;
import com.conversantmedia.mpub.rsql.qbuilder.QBuilder;
import com.conversantmedia.mpub.rsql.qbuilder.expression.*;

import java.util.function.Supplier;

/**
 * Base class for numeric properties
 * @param <T>
 * @param <V>
 */
public abstract class NumberProperty<T extends QBuilder<T>,V> extends ListableProperty<T,V> {

    public NumberProperty(String name, Supplier<Combiner<T>> combinerSupplier, QueryFragment root) {
        super(name, combinerSupplier, root);
    }

    public final Combiner<T> gt(V value) {
        root.addExpression(new GreaterThan(name,serialize(value)));
        return combinerSupplier.get();
    }

    public final Combiner<T> gte(V value) {
        root.addExpression(new GreaterThanOrEquals(name,serialize(value)));
        return combinerSupplier.get();
    }

    public final Combiner<T> lt(V value) {
        root.addExpression(new LessThan(name,serialize(value)));
        return combinerSupplier.get();
    }

    public final Combiner<T> lte(V value) {
        root.addExpression(new LessThanOrEquals(name,serialize(value)));
        return combinerSupplier.get();
    }
}
