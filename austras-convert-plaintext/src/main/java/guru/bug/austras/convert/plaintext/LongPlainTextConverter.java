package guru.bug.austras.convert.plaintext;

import guru.bug.austras.convert.LongContentConverter;

@PlainText
public class LongPlainTextConverter extends AbstractPlainTextConverter<Long> {
    private final LongContentConverter stringConverter;

    public LongPlainTextConverter(@PlainText LongContentConverter stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public Long fromString(String value) {
        if (value == null) {
            return null;
        }
        return stringConverter.fromString(value);
    }

    @Override
    public String toString(Long value) {
        if (value == null) {
            return null;
        }
        return stringConverter.toString(value);
    }
}
