package guru.bug.austras.convert.content.plaintext;

import guru.bug.austras.convert.content.IntegerContentConverter;

@PlainText
public class IntegerPlainTextConverter extends AbstractPlainTextConverter<Integer> {
    private final IntegerContentConverter stringConverter;

    @SuppressWarnings("WeakerAccess")
    public IntegerPlainTextConverter(@PlainText IntegerContentConverter stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public Integer fromString(String value) {
        if (value == null) {
            return null;
        }
        return stringConverter.fromString(value);
    }

    @Override
    public String toString(Integer value) {
        if (value == null) {
            return null;
        }
        return stringConverter.toString(value);
    }
}
