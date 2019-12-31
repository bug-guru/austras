package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.content.ContentConverter;
import guru.bug.austras.convert.content.plaintext.PlainText;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

import java.time.OffsetTime;

@ApplicationJson
public class OffsetTimeJsonConverter extends AbstractJsonConverter<OffsetTime> {
    private final ContentConverter<OffsetTime> converter;

    @SuppressWarnings("WeakerAccess")
    public OffsetTimeJsonConverter(@PlainText ContentConverter<OffsetTime> converter) {
        this.converter = converter;
    }

    @Override
    public void toJson(OffsetTime value, JsonValueWriter writer) {
        var str = converter.toString(value);
        writer.writeString(str);
    }

    @Override
    public OffsetTime fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return converter.fromString(str);
    }

}
