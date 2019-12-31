package guru.bug.austras.convert.content.plaintext;

import java.time.Month;

@PlainText
public class MonthPlainTextConverter extends AbstractPlainTextConverter<Month> {
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
