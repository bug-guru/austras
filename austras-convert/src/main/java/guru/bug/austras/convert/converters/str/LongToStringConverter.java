package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.converters.StringLongConverter;

public class LongToStringConverter implements StringConverter<Long> {
    private final StringLongConverter stringConverter;

    public LongToStringConverter(StringLongConverter stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public Long fromString(String value) {
        if (value == null) {
            return null;
        }
        return stringConverter.fromString(value);
    }

    @Override
    public String toString(Long value) {
        if (value == null) {
            return null;
        }
        return stringConverter.toString(value);
    }
}
