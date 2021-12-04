package com.conversantmedia.rsql.qbuilder.expression;

import com.conversantmedia.rsql.qbuilder.Operators;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * rSQL query fragment, which consists of one or more expression(s)
 */
public class QueryFragment implements Expression {
    @Getter
    private final List<Expression> expressions;
    @Nullable
    @Getter
    private final QueryFragment parent;

    /**
     * Create new query fragment with option parent
     * @param parent - optional parent
     */
    public QueryFragment(@Nullable QueryFragment parent) {
        this(parent,new ArrayList<>());
    }

    /**
     * Used to make shallow clone of the query fragment
     * @param parent parent query fragment
     * @param expressions expressions
     */
    public QueryFragment(@Nullable QueryFragment parent, @NotNull List<Expression> expressions) {
        this.parent = parent;
        this.expressions = expressions;
    }

    /**
     * Add expression to the query fragment
     * @param expression - expression
     */
    public void addExpression(Expression expression) {
        this.expressions.add(expression);
    }

    @Override
    public String serialize() {
        if (expressions.isEmpty()) throw new IllegalStateException("Query with no expressions");

        String fragment = expressions.stream().map(Expression::serialize).collect(Collectors.joining(""));
        if (parent != null) fragment = Operators.BEGIN + fragment + Operators.END;
        return fragment;
    }

    /**
     * Merge with another query fraggment
     * @param other - another query fragment
     * @param combiningExpression = expression that combines these 2 query fragments
     */
    public void mergeWith(QueryFragment other, Expression combiningExpression) {
        this.expressions.add(combiningExpression);
        this.expressions.add(new QueryFragment(this,other.expressions));
    }

    /**
     * Whether this query fragment is empty i.e. no expressions
     */
    public boolean isEmpty() {
        return expressions.isEmpty();
    }
}
