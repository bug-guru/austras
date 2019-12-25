package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.MonthDay;

@Default
public class MonthDayToStringConverter implements StringConverter<MonthDay> {
    @Override
    public MonthDay fromString(String value) {
        if (value == null) {
            return null;
        }
        return MonthDay.parse(value);
    }

    @Override
    public String toString(MonthDay obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }
}
