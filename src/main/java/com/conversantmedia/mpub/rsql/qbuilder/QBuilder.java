package com.conversantmedia.mpub.rsql.qbuilder;

import com.conversantmedia.mpub.rsql.qbuilder.expression.And;
import com.conversantmedia.mpub.rsql.qbuilder.expression.Or;
import com.conversantmedia.mpub.rsql.qbuilder.expression.QueryFragment;
import com.conversantmedia.mpub.rsql.qbuilder.property.*;
import lombok.Getter;

import static lombok.AccessLevel.PROTECTED;

/**
 * All implementations of rSQL query builders extend this class. It provides methods to construct properties as
 * well as additional operations
 * @param <T> actual class that is extending this
 */
public abstract class QBuilder<T extends QBuilder<T>> {

    @Getter(PROTECTED)
    private QueryFragment root = new QueryFragment(null);

    /**
     * Create {@link StringProperty}
     * @param name property name
     * @return property
     */
    protected StringProperty<T> string(String name) {
        return new StringProperty<>(name, CombinerImpl::new,root);
    }

    /**
     * Create {@link LongProperty}
     * @param name property name
     * @return property
     */
    protected LongProperty<T> longNumeric(String name) {
        return new LongProperty<>(name, CombinerImpl::new,root);
    }

    /**
     * Create {@link IntegerProperty}
     * @param name property name
     * @return property
     */
    protected IntegerProperty<T> intNumeric(String name) {
        return new IntegerProperty<>(name, CombinerImpl::new,root);
    }

    /**
     * Create {@link EnumProperty}
     * @param name property name
     * @param <S> Enum type
     * @return property
     */
    protected <S extends Enum<S>> EnumProperty<T,S> enumeration(String name) {
        return new EnumProperty<>(name, CombinerImpl::new,root);
    }

    /**
     * Create {@link BooleanProperty}
     * @param name property name
     * @return property
     */
    protected BooleanProperty<T> bool(String name) {
        return new BooleanProperty<>(name, CombinerImpl::new,root);
    }

    /**
     * Create {@link LocalDateTimeProperty}
     * @param name property name
     * @return property
     */
    protected LocalDateTimeProperty<T> localDateTime(String name) {
        return new LocalDateTimeProperty<T>(name, CombinerImpl::new,root);
    }

    /**
     * Syntactic sugar
     * @return this instance
     */
    @SuppressWarnings("unchecked")
    public T where() {
        return (T) this;
    }

    /**
     * Start sub-query
     * @return this
     */
    @SuppressWarnings("unchecked")
    public T begin() {
        final QueryFragment newRoot = new QueryFragment(root);
        root.addExpression(newRoot);
        root = newRoot;
        return (T) this;
    }

    /**
     * Extend builder to add more predicates
     * @return Combiner
     */
    public Combiner<T> extend() {
        return new CombinerImpl();
    }

    /**
     * Combine this builder with another
     * @param other another instance of the builder
     * @return original builder with query expressions from the other builder added
     */
    @SuppressWarnings("unchecked")
    public T plus(T other) {
        if (this.root.isEmpty()) this.root = new QueryFragment(null, other.getRoot().getExpressions());
        else if (!other.getRoot().isEmpty()) this.root.mergeWith(other.getRoot(),new And());
        return (T) this;
    }

    public String encode() {
        if (root.getParent() != null) throw new IllegalStateException("Number of end() operations doesn't match that of begin() ");
        return root.serialize();
    }

    /**
     * Implementation of {@link Combiner} that
     */
    private class CombinerImpl implements Combiner<T> {

        @SuppressWarnings("unchecked")
        @Override
        public T and() {
            root.addExpression(new And());
            return (T)QBuilder.this;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T or() {
            root.addExpression(new Or());
            return (T)QBuilder.this;
        }

        @Override
        public Combiner<T> end() {
            if (root.getParent() == null) throw new IllegalStateException("Too many end() operations");
            root = root.getParent();
            return this;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T build() {
            if (root.getParent() != null) throw new IllegalStateException("Number of end() operations doesn't match that of begin() ");
            return (T)QBuilder.this;
        }
    }
}
