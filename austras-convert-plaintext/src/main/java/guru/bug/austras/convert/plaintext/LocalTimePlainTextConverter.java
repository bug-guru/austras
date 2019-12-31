package guru.bug.austras.convert.plaintext;

import java.time.LocalTime;

@PlainText
public class LocalTimePlainTextConverter extends AbstractPlainTextConverter<LocalTime> {
    @Override
    public LocalTime fromString(String value) {
        if (value == null) return null;
        return LocalTime.parse(value);
    }

    @Override
    public String toString(LocalTime obj) {
        if (obj == null) return null;
        return obj.toString();
    }
}
