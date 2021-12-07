package com.conversantmedia.mpub.rsql.qbuilder.property.virtual;

import com.conversantmedia.mpub.rsql.qbuilder.Combiner;
import com.conversantmedia.mpub.rsql.qbuilder.QBuilder;
import com.conversantmedia.mpub.rsql.qbuilder.expression.Equals;
import com.conversantmedia.mpub.rsql.qbuilder.expression.NotEquals;
import com.conversantmedia.mpub.rsql.qbuilder.expression.QueryFragment;

import java.util.function.Supplier;

/**
 * Property that can be compared with equals or not equals operators
 * @param <T>
 * @param <V>
 */
public class EquitableProperty<T extends QBuilder<T>,V> extends ExistentialProperty<T>{

    public EquitableProperty(String name, Supplier<Combiner<T>> combinerSupplier, QueryFragment root) {
        super(name, combinerSupplier, root);
    }

    public Combiner<T> eq(V value) {
        root.addExpression(new Equals(name,serialize(value)));
        return combinerSupplier.get();
    }

    public Combiner<T> ne(V value) {
        root.addExpression(new NotEquals(name,serialize(value)));
        return combinerSupplier.get();
    }

    /**
     * Default serialization function just calls toString()
     * @param value value to serialize
     * @return serialized value
     */
    protected String serialize(V value) {
        return value.toString();
    }

}
