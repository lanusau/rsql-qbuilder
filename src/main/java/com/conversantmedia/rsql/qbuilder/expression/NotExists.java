package com.conversantmedia.rsql.qbuilder.expression;

import com.conversantmedia.rsql.qbuilder.Operators;
import lombok.RequiredArgsConstructor;

/**
 * rSQL EX expression with value false
 */
@RequiredArgsConstructor
public class NotExists implements Expression {

    private final String propertyName;

    @Override
    public String serialize() {
        return propertyName + Operators.EX + "false";
    }
}
