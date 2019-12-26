package guru.bug.austras.convert.content.plaintext;

import java.time.OffsetTime;

@PlainText
public class OffsetTimePlainTextConverter extends AbstractPlainTextConverter<OffsetTime> {
    @Override
    public OffsetTime fromString(String value) {
        if (value == null) return null;
        return OffsetTime.parse(value);
    }

    @Override
    public String toString(OffsetTime obj) {
        if (obj == null) return null;
        return obj.toString();
    }
}
