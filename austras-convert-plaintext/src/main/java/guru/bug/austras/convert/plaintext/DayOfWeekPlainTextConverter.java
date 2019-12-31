package guru.bug.austras.convert.plaintext;

import java.time.DayOfWeek;

@PlainText
public class DayOfWeekPlainTextConverter extends AbstractPlainTextConverter<DayOfWeek> {
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
