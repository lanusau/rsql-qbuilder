package com.conversantmedia.mpub.rsql.qbuilder.expression;

import com.conversantmedia.mpub.rsql.qbuilder.Operators;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * rSQL NIN expression
 */
@RequiredArgsConstructor
public class NotIn implements Expression {

    private final String propertyName;
    private final List<String> values;

    @Override
    public String serialize() {
        return propertyName + Operators.NIN + values.stream().collect(Collectors.joining(",","(",")"));
    }
}
