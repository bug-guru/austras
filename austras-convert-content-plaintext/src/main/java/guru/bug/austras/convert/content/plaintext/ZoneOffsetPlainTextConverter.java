package guru.bug.austras.convert.content.plaintext;

import java.time.ZoneOffset;

@PlainText
public class ZoneOffsetPlainTextConverter extends AbstractPlainTextConverter<ZoneOffset> {
    @Override
    public ZoneOffset fromString(String value) {
        if (value == null) return null;
        return ZoneOffset.of(value);
    }

    @Override
    public String toString(ZoneOffset obj) {
        if (obj == null) return null;
        return obj.toString();
    }
}
