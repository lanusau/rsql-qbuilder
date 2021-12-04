package com.conversantmedia.rsql.qbuilder.expression;

import com.conversantmedia.rsql.qbuilder.Operators;

/**
 * rSQL AND operator
 */
public class And implements Expression {
    @Override
    public String serialize() {
        return Operators.AND;
    }
}
