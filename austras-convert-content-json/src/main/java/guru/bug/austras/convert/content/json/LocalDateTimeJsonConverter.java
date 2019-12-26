package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.content.ContentConverter;
import guru.bug.austras.convert.content.plaintext.PlainText;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

import java.time.LocalDateTime;

@ApplicationJson
public class LocalDateTimeJsonConverter extends AbstractJsonConverter<LocalDateTime> {
    private final ContentConverter<LocalDateTime> converter;

    @SuppressWarnings("WeakerAccess")
    public LocalDateTimeJsonConverter(@PlainText ContentConverter<LocalDateTime> converter) {
        this.converter = converter;
    }

    @Override
    public void toJson(LocalDateTime value, JsonValueWriter writer) {
        var str = converter.toString(value);
        writer.writeString(str);
    }

    @Override
    public LocalDateTime fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return converter.fromString(str);
    }
}
