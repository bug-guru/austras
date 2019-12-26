package guru.bug.austras.convert.content.plaintext;

import guru.bug.austras.convert.content.BooleanContentConverter;

@PlainText
public class BooleanPlainTextConverter extends AbstractPlainTextConverter<Boolean> {
    private final BooleanContentConverter converter;

    public BooleanPlainTextConverter(@PlainText BooleanContentConverter converter) {
        this.converter = converter;
    }

    @Override
    public Boolean fromString(String value) {
        if (value == null) {
            return null; //NOSONAR as it is library method - should behave as supposed for all converters
        }
        return converter.fromString(value);
    }

    @Override
    public String toString(Boolean value) {
        if (value == null) {
            return null;
        }
        return converter.toString(value);
    }
}
