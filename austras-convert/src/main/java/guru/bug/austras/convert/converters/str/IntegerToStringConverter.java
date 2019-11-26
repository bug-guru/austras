package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.converters.StringIntegerConverter;
import guru.bug.austras.core.Component;

@Component
public class IntegerToStringConverter implements StringConverter<Integer> {
    private final StringIntegerConverter stringConverter;

    @SuppressWarnings("WeakerAccess")
    public IntegerToStringConverter(StringIntegerConverter stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public Integer fromString(String value) {
        if (value == null) {
            return null;
        }
        return stringConverter.fromString(value);
    }

    @Override
    public String toString(Integer value) {
        if (value == null) {
            return null;
        }
        return stringConverter.toString(value);
    }
}
