package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.core.qualifiers.Default;

import java.math.BigInteger;

@Default
public class BigIntegerToStringConverter implements StringConverter<BigInteger> {
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
