package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringConverter;

import java.time.Year;

public class YearToStringConverter implements StringConverter<Year> {
    @Override
    public Year fromString(String value) {
        if (value == null) return null;
        return Year.parse(value);
    }

    @Override
    public String toString(Year obj) {
        if (obj == null) return null;
        return obj.toString();
    }
}
