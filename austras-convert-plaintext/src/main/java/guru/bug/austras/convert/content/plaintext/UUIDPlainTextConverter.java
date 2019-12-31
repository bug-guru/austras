package guru.bug.austras.convert.content.plaintext;

import java.util.UUID;

@PlainText
public class UUIDPlainTextConverter extends AbstractPlainTextConverter<UUID> {
    @Override
    public UUID fromString(String value) {
        if (value == null) {
            return null;
        }
        return UUID.fromString(value);
    }

    @Override
    public String toString(UUID obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }
}
