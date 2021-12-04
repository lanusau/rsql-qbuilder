package com.conversantmedia.rsql.qbuilder;

import com.conversantmedia.rsql.qbuilder.QBuilder;
import com.conversantmedia.rsql.qbuilder.property.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class QBuilderTest {

    @ParameterizedTest(name = "{index}: {1}")
    @MethodSource("basicQuery")
    void basicQuery(QBuilder<?> qBuilder, String expectedQuery) {
        assertEquals(expectedQuery, qBuilder.encode());
    }

    static Stream<Arguments> basicQuery() {
        return Stream.of(
                arguments(new CarQuery().where().type().eq(Type.SEDAN).build(), "type==SEDAN"),
                arguments(new CarQuery().where().type().ne(Type.SEDAN).build(), "type!=SEDAN"),
                arguments(new CarQuery().where().color().eq(Color.WHITE).build(), "color==WHITE"),
                arguments(new CarQuery().where().color().in(Color.WHITE, Color.BLUE).build(), "color=in=(WHITE,BLUE)"),
                arguments(new CarQuery().where().manufacturer().eq("Tesla").build(), "manufacturer==Tesla"),
                arguments(new CarQuery().where().model().eq("Model 3").build(), "model==\"Model 3\""),
                arguments(new CarQuery().where().serialNumber().eq(118231123L).build(), "serialNumber==118231123"),
                arguments(new CarQuery().where().mileage().eq(100000L).build(), "mileage==100000"),
                arguments(new CarQuery().where().mileage().lt(100000L).build(), "mileage=lt=100000"),
                arguments(new CarQuery().where().mileage().lte(100000L).build(), "mileage=le=100000"),
                arguments(new CarQuery().where().mileage().gt(100000L).build(), "mileage=gt=100000"),
                arguments(new CarQuery().where().mileage().gte(100000L).build(), "mileage=ge=100000"),
                arguments(new CarQuery().where().manufactureDate().eq(LocalDateTime.of(2021, 11, 3, 0, 0, 0)).build(), "manufactureDate==2021-11-03T00:00:00"),
                arguments(new CarQuery().where().dateSold().eq(LocalDateTime.of(2021, 12, 3, 0, 0, 0)).build(), "dateSold==2021-12-03T00:00:00"),
                arguments(new CarQuery().where().modelYear().eq(2021).build(), "modelYear==2021"),
                arguments(new CarQuery().where().passengers().eq(5).build(), "passengers==5"),
                arguments(new CarQuery().where().electric().isTrue().build(), "electric==true"),
                arguments(new CarQuery().where().diesel().isTrue().build(), "diesel==true")
        );
    }


    @ParameterizedTest(name = "{index}: {1}")
    @MethodSource("complexQuery")
    void complexQuery(QBuilder<?> qBuilder, String expectedQuery) {
        assertEquals(expectedQuery, qBuilder.encode());
    }

    static Stream<Arguments> complexQuery() {
        return Stream.of(
                arguments(
                        new CarQuery().where()
                                .begin()
                                .type().eq(Type.TRUCK).or().type().eq(Type.SUV)
                                .end()
                                .and()
                                .manufacturer().in("Ford", "GMC")
                                .build(),
                        "(type==TRUCK,type==SUV);manufacturer=in=(Ford,GMC)"
                )
        );
    }

    private static class CarQuery extends QBuilder<CarQuery> {

        public StringProperty<CarQuery> manufacturer() {
            return string("manufacturer");
        }

        public StringProperty<CarQuery> model() {
            return string("model");
        }

        public LongProperty<CarQuery> serialNumber() {
            return longNumeric("serialNumber");
        }

        public LongProperty<CarQuery> mileage() {
            return longNumeric("mileage");
        }

        public LocalDateTimeProperty<CarQuery> manufactureDate() {
            return localDateTime("manufactureDate");
        }

        public LocalDateTimeProperty<CarQuery> dateSold() {
            return localDateTime("dateSold");
        }

        public IntegerProperty<CarQuery> modelYear() {
            return intNumeric("modelYear");
        }

        public IntegerProperty<CarQuery> passengers() {
            return intNumeric("passengers");
        }

        public EnumProperty<CarQuery, Type> type() {
            return enumeration("type");
        }

        public EnumProperty<CarQuery, Color> color() {
            return enumeration("color");
        }

        public BooleanProperty<CarQuery> electric() {
            return bool("electric");
        }

        public BooleanProperty<CarQuery> diesel() {
            return bool("diesel");
        }

    }

    private enum Type {
        SEDAN, COUPE, SUV, TRUCK
    }

    private enum Color {
        WHITE, BLACK, BLUE, RED, GRAY
    }
}
