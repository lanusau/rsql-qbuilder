package com.conversantmedia.mpub.rsql.qbuilder.expression;

import com.conversantmedia.mpub.rsql.qbuilder.Operators;
import lombok.RequiredArgsConstructor;

/**
 * rSQL EX expression
 */
@RequiredArgsConstructor
public class Exists implements Expression {

    private final String propertyName;

    @Override
    public String serialize() {
        return propertyName + Operators.EX + "true";
    }
}
