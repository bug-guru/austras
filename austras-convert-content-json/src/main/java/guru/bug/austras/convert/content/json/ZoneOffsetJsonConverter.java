package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.content.ContentConverter;
import guru.bug.austras.convert.content.plaintext.PlainText;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

import java.time.ZoneOffset;

@ApplicationJson
public class ZoneOffsetJsonConverter extends AbstractJsonConverter<ZoneOffset> {
    private final ContentConverter<ZoneOffset> converter;

    @SuppressWarnings("WeakerAccess")
    public ZoneOffsetJsonConverter(@PlainText ContentConverter<ZoneOffset> converter) {
        this.converter = converter;
    }

    @Override
    public void toJson(ZoneOffset value, JsonValueWriter writer) {
        var str = converter.toString(value);
        writer.writeString(str);
    }

    @Override
    public ZoneOffset fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return converter.fromString(str);
    }

}
