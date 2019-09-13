package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.converters.StringShortConverter;
import guru.bug.austras.core.Component;

@Component
public class ShortToStringConverter implements StringConverter<Short> {
    private final StringShortConverter stringConverter;

    public ShortToStringConverter(StringShortConverter stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public Short fromString(String value) {
        if (value == null) {
            return null;
        }
        return stringConverter.fromString(value);
    }

    @Override
    public String toString(Short value) {
        if (value == null) {
            return null;
        }
        return stringConverter.toString(value);
    }
}
