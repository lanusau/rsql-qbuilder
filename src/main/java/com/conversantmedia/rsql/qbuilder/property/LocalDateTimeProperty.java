package com.conversantmedia.rsql.qbuilder.property;

import com.conversantmedia.rsql.qbuilder.Combiner;
import com.conversantmedia.rsql.qbuilder.QBuilder;
import com.conversantmedia.rsql.qbuilder.expression.QueryFragment;
import com.conversantmedia.rsql.qbuilder.property.virtual.TemporalProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.function.Supplier;

/**
 * Property for the local date and time
 * @param <T>
 */
public class LocalDateTimeProperty<T extends QBuilder<T>> extends TemporalProperty<T,LocalDateTime> {

    public LocalDateTimeProperty(String name, Supplier<Combiner<T>> combinerSupplier, QueryFragment root) {
        super(name, combinerSupplier, root);
    }

    @Override
    protected String convertToString(LocalDateTime datetime) {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(datetime.truncatedTo(ChronoUnit.SECONDS));
    }
}
