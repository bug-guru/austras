package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.converters.StringDoubleConverter;
import guru.bug.austras.core.qualifiers.Default;

@Default
public class DoubleToStringConverter implements StringConverter<Double> {
    private final StringDoubleConverter stringConverter;

    public DoubleToStringConverter(StringDoubleConverter stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public Double fromString(String value) {
        if (value == null) {
            return null;
        }
        return stringConverter.fromString(value);
    }

    @Override
    public String toString(Double value) {
        if (value == null) {
            return null;
        }
        return stringConverter.toString(value);
    }
}
