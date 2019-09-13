package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.core.Component;

import java.math.BigDecimal;

@Component
public class BigDecimalToStringConverter implements StringConverter<BigDecimal> {
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
