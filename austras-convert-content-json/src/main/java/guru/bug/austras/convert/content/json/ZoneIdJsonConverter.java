package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.content.ContentConverter;
import guru.bug.austras.convert.content.plaintext.PlainText;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

import java.time.ZoneId;

@ApplicationJson
public class ZoneIdJsonConverter extends AbstractJsonConverter<ZoneId> {
    private final ContentConverter<ZoneId> converter;

    @SuppressWarnings("WeakerAccess")
    public ZoneIdJsonConverter(@PlainText ContentConverter<ZoneId> converter) {
        this.converter = converter;
    }

    @Override
    public void toJson(ZoneId value, JsonValueWriter writer) {
        var str = converter.toString(value);
        writer.writeString(str);
    }

    @Override
    public ZoneId fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return converter.fromString(str);
    }

}
