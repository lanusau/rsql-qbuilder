package com.conversantmedia.mpub.rsql.qbuilder.property.virtual;

import com.conversantmedia.mpub.rsql.qbuilder.Combiner;
import com.conversantmedia.mpub.rsql.qbuilder.QBuilder;
import com.conversantmedia.mpub.rsql.qbuilder.expression.*;

import java.util.function.Supplier;

/**
 * Base class for temporal properties
 * @param <T>
 * @param <V>
 */
public abstract class TemporalProperty<T extends QBuilder<T>,V> extends EquitableProperty<T,V> {

    public TemporalProperty(String name, Supplier<Combiner<T>> combinerSupplier, QueryFragment root) {
        super(name, combinerSupplier, root);
    }

    /**
     * Convert temporal property to string
     */
    protected abstract String convertToString(V datetime);

    public Combiner<T> before(V dateTime) {
        root.addExpression(new LessThan(name,serialize(dateTime)));
        return combinerSupplier.get();
    }

    public Combiner<T> after(V dateTime) {
        root.addExpression(new GreaterThan(name,serialize(dateTime)));
        return combinerSupplier.get();
    }

    public Combiner<T> between(V startDateTime, V endDateTime) {
        root.addExpression(new GreaterThanOrEquals(name,serialize(startDateTime)));
        root.addExpression(new And());
        root.addExpression(new LessThan(name,serialize(endDateTime)));
        return combinerSupplier.get();
    }


    @Override
    protected String serialize(V value) {
        return convertToString(value);
    }
}
