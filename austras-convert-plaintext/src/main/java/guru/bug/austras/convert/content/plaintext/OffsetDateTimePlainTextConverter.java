package guru.bug.austras.convert.content.plaintext;

import java.time.OffsetDateTime;

@PlainText
public class OffsetDateTimePlainTextConverter extends AbstractPlainTextConverter<OffsetDateTime> {
    @Override
    public OffsetDateTime fromString(String value) {
        if (value == null) return null;
        return OffsetDateTime.parse(value);
    }

    @Override
    public String toString(OffsetDateTime obj) {
        if (obj == null) return null;
        return obj.toString();
    }
}
