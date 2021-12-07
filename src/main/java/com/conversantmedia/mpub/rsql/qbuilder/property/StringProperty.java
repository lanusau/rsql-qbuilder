package com.conversantmedia.mpub.rsql.qbuilder.property;

import com.conversantmedia.mpub.rsql.qbuilder.Combiner;
import com.conversantmedia.mpub.rsql.qbuilder.QBuilder;
import com.conversantmedia.mpub.rsql.qbuilder.expression.Equals;
import com.conversantmedia.mpub.rsql.qbuilder.expression.QueryFragment;
import com.conversantmedia.mpub.rsql.qbuilder.property.virtual.ListableProperty;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

/**
 * String property
 * @param <T>
 */
public class StringProperty<T extends QBuilder<T>> extends ListableProperty<T,String> {

    private static final CharSequence DOUBLE_QUOTE = "\"";
    private static final CharSequence SINGLE_QUOTE = "'";
    private static final Set<Character> RESERVED_CHARACTERS = new HashSet<>(Arrays.asList(
            ' ',
            '\"',
            '\'',
            '(',
            ')',
            ';',
            ',',
            '=',
            '!',
            '~',
            '<',
            '>'

    ));

    public StringProperty(String name, Supplier<Combiner<T>> combinerSupplier, QueryFragment root) {
        super(name, combinerSupplier,root);
    }

    public final Combiner<T> matchAll(String value) {
        root.addExpression(new Equals(name,serialize("*" + value + "*")));
        return combinerSupplier.get();
    }

    /**
     * Per rsql-parser wiki:
     * <p />
     * Argument can be a single value, or multiple values in parenthesis separated by comma.
     * Value that doesn't contain any reserved character or a white space can be unquoted, other arguments
     * must be enclosed in single or double quotes.
     * <p />
     * If you need to use both single and double quotes inside a quoted argument, then you must escape
     * one of them using \ (backslash). If you want to use \ literally, then double it as \\. Backslash has a
     * special meaning only inside a quoted argument, not in unquoted argument.
     * <p />
     * @param value value to serialize
     * @return - serialized value
     */
    @Override
    protected String serialize(String value) {

        final StringBuilder sb = new StringBuilder(value.length()*3);

        boolean containsDoubleQuotes = false;
        boolean containsSingleQuotes = false;
        boolean containsReservedCharacters = false;

        // First pass to escape "\" and check for presence of quotes
        for (int i = 0; i < value.length(); i++) {
            final char c = value.charAt(i);
            if (c == '\\') sb.append('\\');
            if (c == '\"') containsDoubleQuotes = true;
            if (c == '\'') containsSingleQuotes = true;
            if (RESERVED_CHARACTERS.contains(c)) containsReservedCharacters = true;

            sb.append(c);
        }

        final String escapedValue = sb.toString();

        if(!containsReservedCharacters) return escapedValue;
        else if (!containsDoubleQuotes) return DOUBLE_QUOTE + escapedValue + DOUBLE_QUOTE;
        else if (!containsSingleQuotes) return SINGLE_QUOTE + escapedValue + SINGLE_QUOTE;
        else {
            // Need one more pass to escape single quotes
            sb.setLength(0);
            for (int i = 0; i < escapedValue.length(); i++) {
                final char c = escapedValue.charAt(i);
                if (c == '\'') sb.append('\\');
                sb.append(c);
            }
            return DOUBLE_QUOTE + sb.toString() + DOUBLE_QUOTE;
        }
    }
}
