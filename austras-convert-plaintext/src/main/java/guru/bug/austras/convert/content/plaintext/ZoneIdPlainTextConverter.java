package guru.bug.austras.convert.content.plaintext;

import java.time.ZoneId;

@PlainText
public class ZoneIdPlainTextConverter extends AbstractPlainTextConverter<ZoneId> {
    @Override
    public ZoneId fromString(String value) {
        if (value == null) return null;
        return ZoneId.of(value);
    }

    @Override
    public String toString(ZoneId obj) {
        if (obj == null) return null;
        return obj.toString();
    }
}
