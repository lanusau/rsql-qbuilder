package com.conversantmedia.mpub.rsql.qbuilder.expression;

import com.conversantmedia.mpub.rsql.qbuilder.Operators;
import lombok.RequiredArgsConstructor;

/**
 * rSQL LT expression
 */
@RequiredArgsConstructor
public class LessThan implements Expression {

    private final String propertyName;
    private final String value;

    @Override
    public String serialize() {
        return propertyName + Operators.LT + value;
    }
}
