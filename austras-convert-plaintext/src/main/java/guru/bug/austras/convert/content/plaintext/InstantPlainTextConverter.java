package guru.bug.austras.convert.content.plaintext;

import java.time.Instant;

@PlainText
public class InstantPlainTextConverter extends AbstractPlainTextConverter<Instant> {
    @Override
    public Instant fromString(String value) {
        if (value == null) return null;
        return Instant.parse(value);
    }

    @Override
    public String toString(Instant obj) {
        if (obj == null) return null;
        return obj.toString();
    }
}
