package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.core.Component;

import java.time.DayOfWeek;

@Component
public class DayOfWeekToStringConverter implements StringConverter<DayOfWeek> {
    @Override
    public DayOfWeek fromString(String value) {
        if (value == null) return null;
        return DayOfWeek.valueOf(value);
    }

    @Override
    public String toString(DayOfWeek obj) {
        if (obj == null) return null;
        return obj.name();
    }
}
