package com.conversantmedia.rsql.qbuilder.expression;

import com.conversantmedia.rsql.qbuilder.Operators;

/**
 * rSQL OR expression
 */
public class Or implements Expression {
    @Override
    public String serialize() {
        return Operators.OR;
    }
}
