package com.conversantmedia.rsql.qbuilder.expression;

import com.conversantmedia.rsql.qbuilder.Operators;
import lombok.RequiredArgsConstructor;

/**
 * rSQL GT expression
 */
@RequiredArgsConstructor
public class GreaterThan implements Expression {

    private final String propertyName;
    private final String value;

    @Override
    public String serialize() {
        return propertyName + Operators.GT + value;
    }
}
