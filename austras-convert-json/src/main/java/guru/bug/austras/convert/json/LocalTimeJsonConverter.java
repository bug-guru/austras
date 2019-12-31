package guru.bug.austras.convert.json;

import guru.bug.austras.convert.ContentConverter;
import guru.bug.austras.convert.plaintext.PlainText;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

import java.time.LocalTime;

@ApplicationJson
public class LocalTimeJsonConverter extends AbstractJsonConverter<LocalTime> {
    private final ContentConverter<LocalTime> converter;

    @SuppressWarnings("WeakerAccess")
    public LocalTimeJsonConverter(@PlainText ContentConverter<LocalTime> converter) {
        this.converter = converter;
    }

    @Override
    public void toJson(LocalTime value, JsonValueWriter writer) {
        var str = converter.toString(value);
        writer.writeString(str);
    }

    @Override
    public LocalTime fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return converter.fromString(str);
    }
}
