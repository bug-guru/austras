package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringBooleanConverter;
import guru.bug.austras.core.qualifiers.Default;

@Default
public class PrimitiveBooleanToStringConverter implements StringBooleanConverter {
    @Override
    public boolean fromString(String value) {
        return Boolean.parseBoolean(value);
    }

    @Override
    public String toString(boolean obj) {
        return String.valueOf(obj);
    }
}
