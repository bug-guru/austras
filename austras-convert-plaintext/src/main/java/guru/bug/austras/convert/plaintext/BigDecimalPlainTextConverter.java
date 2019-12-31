package guru.bug.austras.convert.plaintext;

import java.math.BigDecimal;

@PlainText
public class BigDecimalPlainTextConverter extends AbstractPlainTextConverter<BigDecimal> {
    @Override
    public BigDecimal fromString(String value) {
        if (value == null) {
            return null;
        }
        return new BigDecimal(value);
    }

    @Override
    public String toString(BigDecimal obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

}
