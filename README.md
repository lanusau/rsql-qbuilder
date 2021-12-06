# rSQL query builder

This package helps to build rSQL queries programmatically.

## Example Query class

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

## Query examples using class above

    final CarQuery query = new CarQuery().where()
    .begin()
    .type().eq(Type.TRUCK).or().type().eq(Type.SUV)
    .end()
    .and()
    .manufacturer().in("Ford", "GMC")
    .build();

    final String ecoded = query.encode();

Results in follow string `(type==TRUCK,type==SUV);manufacturer=in=(Ford,GMC)`


For more examples, see tests in QBuilderTest class. 
