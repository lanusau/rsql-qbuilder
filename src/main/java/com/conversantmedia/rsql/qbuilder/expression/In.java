package com.conversantmedia.rsql.qbuilder.expression;

import com.conversantmedia.rsql.qbuilder.Operators;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * rSQL IN expression
 */
@RequiredArgsConstructor
public class In implements Expression {

    private final String propertyName;
    private final List<String> values;

    @Override
    public String serialize() {
        return propertyName + Operators.IN + values.stream().collect(Collectors.joining(",","(",")"));
    }
}
