package com.conversantmedia.mpub.rsql.qbuilder;

import com.conversantmedia.mpub.rsql.qbuilder.property.*;
import org.junit.jupiter.api.Test;
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
                arguments(new CarQuery().where().color().notIn(Color.WHITE, Color.BLUE).build(), "color=out=(WHITE,BLUE)"),
                arguments(new CarQuery().where().manufacturer().eq("Tesla").build(), "manufacturer==Tesla"),
                arguments(new CarQuery().where().manufacturer().eq("L'Car\"French\"").build(), "manufacturer==\"L\\'Car\"French\"\""),
                arguments(new CarQuery().where().manufacturer().matchAll("Tesla").build(), "manufacturer==*Tesla*"),
                arguments(new CarQuery().where().model().eq("Model 3").build(), "model==\"Model 3\""),
                arguments(new CarQuery().where().serialNumber().eq(118231123L).build(), "serialNumber==118231123"),
                arguments(new CarQuery().where().mileage().eq(100000L).build(), "mileage==100000"),
                arguments(new CarQuery().where().mileage().lt(100000L).build(), "mileage=lt=100000"),
                arguments(new CarQuery().where().mileage().lte(100000L).build(), "mileage=le=100000"),
                arguments(new CarQuery().where().mileage().gt(100000L).build(), "mileage=gt=100000"),
                arguments(new CarQuery().where().mileage().gte(100000L).build(), "mileage=ge=100000"),
                arguments(new CarQuery().where().manufactureDate().eq(LocalDateTime.of(2021, 11, 3, 0, 0, 0)).build(), "manufactureDate==2021-11-03T00:00:00"),
                arguments(new CarQuery().where().dateSold().after(LocalDateTime.of(2021, 12, 3, 0, 0, 0)).build(), "dateSold=gt=2021-12-03T00:00:00"),
                arguments(new CarQuery().where().dateSold().before(LocalDateTime.of(2021, 12, 3, 0, 0, 0)).build(), "dateSold=lt=2021-12-03T00:00:00"),
                arguments(new CarQuery().where().dateSold().between(LocalDateTime.of(2021, 12, 3, 0, 0, 0), LocalDateTime.of(2021, 12, 5, 0, 0, 0)).build(), "dateSold=ge=2021-12-03T00:00:00;dateSold=lt=2021-12-05T00:00:00"),
                arguments(new CarQuery().where().modelYear().eq(2021).build(), "modelYear==2021"),
                arguments(new CarQuery().where().passengers().eq(5).build(), "passengers==5"),
                arguments(new CarQuery().where().electric().isTrue().build(), "electric==true"),
                arguments(new CarQuery().where().diesel().isFalse().build(), "diesel==false"),
                arguments(new CarQuery().where().mileage().exists().build(), "mileage=ex=true"),
                arguments(new CarQuery().where().mileage().notExists().build(), "mileage=ex=false")
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
                ),
                arguments(new CarQuery().where()
                                .electric().isTrue()
                                .and()
                                .passengers().gt(4)
                                .and()
                                .type().in(Type.COUPE, Type.SEDAN)
                                .build(),
                        "electric==true;passengers=gt=4;type=in=(COUPE,SEDAN)"
                ),
                arguments(new CarQuery().where()
                                .begin()
                                    .type().eq(Type.SEDAN).or().type().eq(Type.COUPE)
                                .end()
                                .and()
                                .begin()
                                    .manufacturer().eq("Tesla").or().manufacturer().eq("Ford")
                                .end()
                                .and()
                                .modelYear().gt(2021)
                                .build(),
                        "(type==SEDAN,type==COUPE);(manufacturer==Tesla,manufacturer==Ford);modelYear=gt=2021"
                )
        );
    }

    @Test
    void testExtend() {
        // Given: a query
        final CarQuery query = new CarQuery().where().manufacturer().eq("Tesla").build();

        // When: query is extended
        final CarQuery extended = query.extend()
                .and()
                .modelYear().gt(2021).build();

        // Then: result is correct
        assertEquals("manufacturer==Tesla;modelYear=gt=2021", extended.encode());
    }

    @Test
    void testPlus() {
        // Given: 2 queries
        final CarQuery query1 = new CarQuery().where().manufacturer().eq("Tesla").build();
        final CarQuery query2 = new CarQuery().where().modelYear().gt(2021).build();

        // When: queries are added
        final CarQuery combined = query1.plus(query2);

        // Then: result is correct
        assertEquals("manufacturer==Tesla;(modelYear=gt=2021)", combined.encode());
    }

    @Test
    void testPlusWithEmptyQuery() {
        // Given: empty query
        final CarQuery query1 = new CarQuery();

        // When: another query is added to empty query
        final CarQuery combined = query1.plus( new CarQuery().where().modelYear().gt(2021).build());

        // Then: result is correct
        assertEquals("modelYear=gt=2021", combined.encode());
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
