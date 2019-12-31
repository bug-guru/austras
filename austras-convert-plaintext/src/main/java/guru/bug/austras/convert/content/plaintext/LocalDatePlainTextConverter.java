package guru.bug.austras.convert.content.plaintext;

import java.time.LocalDate;

@PlainText
public class LocalDatePlainTextConverter extends AbstractPlainTextConverter<LocalDate> {
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
