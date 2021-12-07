package com.conversantmedia.mpub.rsql.qbuilder.expression;

/**
 * Interface for all expressions
 */
public interface Expression {
    /**
     * Serialized expression to rSQL string value
     * @return - rSQL string value
     */
    String serialize();
}
