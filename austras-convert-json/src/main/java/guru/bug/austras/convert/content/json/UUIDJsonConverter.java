package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.content.ContentConverter;
import guru.bug.austras.convert.content.plaintext.PlainText;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

import java.util.UUID;

@ApplicationJson
public class UUIDJsonConverter extends AbstractJsonConverter<UUID> {
    private final ContentConverter<UUID> converter;

    @SuppressWarnings("WeakerAccess")
    public UUIDJsonConverter(@PlainText ContentConverter<UUID> converter) {
        this.converter = converter;
    }

    @Override
    public void toJson(UUID value, JsonValueWriter writer) {
        var str = converter.toString(value);
        writer.writeString(str);
    }

    @Override
    public UUID fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return converter.fromString(str);
    }

}
