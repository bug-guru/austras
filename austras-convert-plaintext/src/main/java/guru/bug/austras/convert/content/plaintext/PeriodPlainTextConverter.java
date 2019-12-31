package guru.bug.austras.convert.content.plaintext;

import java.time.Period;

@PlainText
public class PeriodPlainTextConverter extends AbstractPlainTextConverter<Period> {
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
