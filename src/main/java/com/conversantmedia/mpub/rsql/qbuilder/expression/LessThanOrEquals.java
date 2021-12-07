package com.conversantmedia.mpub.rsql.qbuilder.expression;

import com.conversantmedia.mpub.rsql.qbuilder.Operators;
import lombok.RequiredArgsConstructor;

/**
 * rSQL LTE expression
 */
@RequiredArgsConstructor
public class LessThanOrEquals implements Expression {

    private final String propertyName;
    private final String value;

    @Override
    public String serialize() {
        return propertyName + Operators.LTE + value;
    }
}
