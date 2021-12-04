package com.conversantmedia.rsql.qbuilder.expression;

import com.conversantmedia.rsql.qbuilder.Operators;
import lombok.RequiredArgsConstructor;

/**
 * rSQL EQ expression
 */
@RequiredArgsConstructor
public class Equals implements Expression {

    private final String propertyName;
    private final String value;

    @Override
    public String serialize() {
        return propertyName + Operators.EQ + value;
    }
}
