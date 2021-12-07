package com.conversantmedia.mpub.rsql.qbuilder.property.virtual;

import com.conversantmedia.mpub.rsql.qbuilder.Combiner;
import com.conversantmedia.mpub.rsql.qbuilder.QBuilder;
import com.conversantmedia.mpub.rsql.qbuilder.expression.Exists;
import com.conversantmedia.mpub.rsql.qbuilder.expression.NotExists;
import com.conversantmedia.mpub.rsql.qbuilder.expression.QueryFragment;

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
