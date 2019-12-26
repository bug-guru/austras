package guru.bug.austras.convert.content.plaintext;

import java.time.YearMonth;

@PlainText
public class YearMonthPlainTextConverter extends AbstractPlainTextConverter<YearMonth> {
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
