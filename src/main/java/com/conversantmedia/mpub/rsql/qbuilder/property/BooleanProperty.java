package com.conversantmedia.mpub.rsql.qbuilder.property;

import com.conversantmedia.mpub.rsql.qbuilder.Combiner;
import com.conversantmedia.mpub.rsql.qbuilder.QBuilder;
import com.conversantmedia.mpub.rsql.qbuilder.expression.Equals;
import com.conversantmedia.mpub.rsql.qbuilder.expression.QueryFragment;
import com.conversantmedia.mpub.rsql.qbuilder.property.virtual.ExistentialProperty;

import java.util.function.Supplier;

/**
 * Boolean property
 * @param <T>
 */
public class BooleanProperty<T extends QBuilder<T>> extends ExistentialProperty<T> {

    public BooleanProperty(String name, Supplier<Combiner<T>> combinerSupplier, QueryFragment root) {
        super(name, combinerSupplier, root);
    }

    /**
     * True expression
     */
    public Combiner<T> isTrue() {
        root.addExpression(new Equals(name,"true"));
        return combinerSupplier.get();
    }

    /**
     * False expression
     */
    public Combiner<T> isFalse() {
        root.addExpression(new Equals(name,"false"));
        return combinerSupplier.get();
    }
}
