package guru.bug.austras.convert.content.plaintext;

import java.math.BigInteger;

@PlainText
public class BigIntegerPlainTextConverter extends AbstractPlainTextConverter<BigInteger> {
    @Override
    public BigInteger fromString(String value) {
        if (value == null) {
            return null;
        }
        return new BigInteger(value);
    }

    @Override
    public String toString(BigInteger obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }
}
