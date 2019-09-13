package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.core.Component;

import java.time.Month;

@Component
public class MonthToStringConverter implements StringConverter<Month> {
    @Override
    public Month fromString(String value) {
        if (value == null) return null;
        return Month.valueOf(value);
    }

    @Override
    public String toString(Month obj) {
        if (obj == null) return null;
        return obj.name();
    }
}
