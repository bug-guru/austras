package guru.bug.austras.convert.plaintext;

import java.time.MonthDay;

@PlainText
public class MonthDayPlainTextConverter extends AbstractPlainTextConverter<MonthDay> {
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
