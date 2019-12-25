package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.YearMonth;

@Default
public class YearMonthToStringConverter implements StringConverter<YearMonth> {
    @Override
    public YearMonth fromString(String value) {
        if (value == null) return null;
        return YearMonth.parse(value);
    }

    @Override
    public String toString(YearMonth obj) {
        if (obj == null) return null;
        return obj.toString();
    }
}
