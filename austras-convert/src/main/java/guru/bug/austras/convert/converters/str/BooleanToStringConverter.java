package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringBooleanConverter;
import guru.bug.austras.convert.converters.StringConverter;

public class BooleanToStringConverter implements StringConverter<Boolean> {
    private final StringBooleanConverter stringConverter;

    public BooleanToStringConverter(StringBooleanConverter stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public Boolean fromString(String value) {
        if (value == null) {
            return null;
        }
        return stringConverter.fromString(value);
    }

    @Override
    public String toString(Boolean value) {
        if (value == null) {
            return null;
        }
        return stringConverter.toString(value);
    }
}
