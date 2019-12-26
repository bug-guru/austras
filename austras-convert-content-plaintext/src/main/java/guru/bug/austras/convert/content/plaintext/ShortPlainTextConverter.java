package guru.bug.austras.convert.content.plaintext;

import guru.bug.austras.convert.content.ShortContentConverter;

@PlainText
public class ShortPlainTextConverter extends AbstractPlainTextConverter<Short> {
    private final ShortContentConverter stringConverter;

    public ShortPlainTextConverter(@PlainText ShortContentConverter stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public Short fromString(String value) {
        if (value == null) {
            return null;
        }
        return stringConverter.fromString(value);
    }

    @Override
    public String toString(Short value) {
        if (value == null) {
            return null;
        }
        return stringConverter.toString(value);
    }
}
