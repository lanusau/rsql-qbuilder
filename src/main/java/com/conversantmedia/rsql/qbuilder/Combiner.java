package com.conversantmedia.rsql.qbuilder;

/**
 * Combiner provides operations to happen after expression
 * @param <T> query builder class
 */
public interface Combiner<T> {
    /**
     * Add AND operation
     * @return query builder
     */
    T and();

    /**
     * Add OR operation
     * @return query builder
     */
    T or();

    /**
     * End sub query. This will fail if already at the top level query
     * @return combiner
     */
    Combiner<T> end();

    /**
     * Build query builder
     * @return query builder
     */
    T build();
}
