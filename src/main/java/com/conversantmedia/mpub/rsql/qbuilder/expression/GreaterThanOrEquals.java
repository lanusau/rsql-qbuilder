package com.conversantmedia.mpub.rsql.qbuilder.expression;

import com.conversantmedia.mpub.rsql.qbuilder.Operators;
import lombok.RequiredArgsConstructor;

/**
 * rSQL GTE expression
 */
@RequiredArgsConstructor
public class GreaterThanOrEquals implements Expression {

    private final String propertyName;
    private final String value;

    @Override
    public String serialize() {
        return propertyName + Operators.GTE + value;
    }
}
