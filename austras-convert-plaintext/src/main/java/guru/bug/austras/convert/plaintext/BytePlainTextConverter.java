package guru.bug.austras.convert.plaintext;

import guru.bug.austras.convert.ByteContentConverter;

@PlainText
public class BytePlainTextConverter extends AbstractPlainTextConverter<Byte> {
    private final ByteContentConverter converter;

    public BytePlainTextConverter(@PlainText ByteContentConverter converter) {
        this.converter = converter;
    }

    @Override
    public Byte fromString(String value) {
        if (value == null) {
            return null;
        }
        return converter.fromString(value);
    }

    @Override
    public String toString(Byte value) {
        if (value == null) {
            return null;
        }
        return converter.toString(value);
    }
}
