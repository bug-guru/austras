package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.core.Component;

import java.time.Period;

@Component
public class PeriodToStringConverter implements StringConverter<Period> {
    @Override
    public Period fromString(String value) {
        if (value == null) return null;
        return Period.parse(value);
    }

    @Override
    public String toString(Period obj) {
        if (obj == null) return null;
        return obj.toString();
    }
}
