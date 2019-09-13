package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringDoubleConverter;
import guru.bug.austras.core.Component;

@Component
public class PrimitiveDoubleToStringConverter implements StringDoubleConverter {
    @Override
    public double fromString(String value) {
        return Double.parseDouble(value);
    }

    @Override
    public String toString(double value) {
        return Double.toString(value);
    }
}
