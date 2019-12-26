package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.content.ContentConverter;
import guru.bug.austras.convert.content.plaintext.PlainText;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

import java.time.OffsetDateTime;

@ApplicationJson
public class OffsetDateTimeJsonConverter extends AbstractJsonConverter<OffsetDateTime> {
    private final ContentConverter<OffsetDateTime> converter;

    @SuppressWarnings("WeakerAccess")
    public OffsetDateTimeJsonConverter(@PlainText ContentConverter<OffsetDateTime> converter) {
        this.converter = converter;
    }

    @Override
    public void toJson(OffsetDateTime value, JsonValueWriter writer) {
        var str = converter.toString(value);
        writer.writeString(str);
    }

    @Override
    public OffsetDateTime fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return converter.fromString(str);
    }

}
