package guru.bug.austras.convert.content.plaintext;

import guru.bug.austras.convert.content.FloatContentConverter;

@PlainText
public class FloatPlainTextConverter extends AbstractPlainTextConverter<Float> {
    private final FloatContentConverter stringConverter;

    public FloatPlainTextConverter(@PlainText FloatContentConverter stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public Float fromString(String value) {
        if (value == null) {
            return null;
        }
        return stringConverter.fromString(value);
    }

    @Override
    public String toString(Float value) {
        if (value == null) {
            return null;
        }
        return stringConverter.toString(value);
    }
}
