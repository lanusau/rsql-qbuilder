package com.conversantmedia.rsql.qbuilder.property.virtual;

import com.conversantmedia.rsql.qbuilder.Combiner;
import com.conversantmedia.rsql.qbuilder.QBuilder;
import com.conversantmedia.rsql.qbuilder.expression.In;
import com.conversantmedia.rsql.qbuilder.expression.NotIn;
import com.conversantmedia.rsql.qbuilder.expression.QueryFragment;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Property that can be compared to a list
 * @param <T>
 * @param <V>
 */
public abstract class ListableProperty<T extends QBuilder<T>,V> extends EquitableProperty<T,V> {

    public ListableProperty(String name, Supplier<Combiner<T>> combinerSupplier, QueryFragment root) {
        super(name, combinerSupplier, root);
    }

    @SafeVarargs
    public final Combiner<T> in(V ... values) {
        return in(Arrays.asList(values));
    }
    public final Combiner<T> in(List<V> values) {
        root.addExpression(new In(name,values.stream().map(this::serialize).collect(Collectors.toList())));
        return combinerSupplier.get();
    }

    @SafeVarargs
    public final Combiner<T> notIn(V ... values) {
        return notIn(Arrays.asList(values));
    }

    public final Combiner<T> notIn(List<V> values) {
        root.addExpression(new NotIn(name,values.stream().map(this::serialize).collect(Collectors.toList())));
        return combinerSupplier.get();
    }
}
