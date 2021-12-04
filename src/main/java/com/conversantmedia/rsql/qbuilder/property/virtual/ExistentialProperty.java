package com.conversantmedia.rsql.qbuilder.property.virtual;

import com.conversantmedia.rsql.qbuilder.Combiner;
import com.conversantmedia.rsql.qbuilder.QBuilder;
import com.conversantmedia.rsql.qbuilder.expression.Exists;
import com.conversantmedia.rsql.qbuilder.expression.NotExists;
import com.conversantmedia.rsql.qbuilder.expression.QueryFragment;

import java.util.function.Supplier;

/**
 * Property that can be checked for existence
 * @param <T>
 */
public abstract class ExistentialProperty<T extends QBuilder<T>> extends Property{
    protected final Supplier<Combiner<T>> combinerSupplier;
    protected final QueryFragment root;

    public ExistentialProperty(String name, Supplier<Combiner<T>> combinerSupplier, QueryFragment root) {
        super(name);
        this.combinerSupplier = combinerSupplier;
        this.root = root;
    }

    public Combiner<T> exists() {
        root.addExpression(new Exists(name));
        return combinerSupplier.get();
    }

    public Combiner<T> notExists() {
        root.addExpression(new NotExists(name));
        return combinerSupplier.get();
    }
}
