package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.LocalDate;

@Default
public class LocalDateToStringConverter implements StringConverter<LocalDate> {
    @Override
    public LocalDate fromString(String value) {
        if (value == null) return null;
        return LocalDate.parse(value);
    }

    @Override
    public String toString(LocalDate obj) {
        if (obj == null) return null;
        return obj.toString();
    }
}
