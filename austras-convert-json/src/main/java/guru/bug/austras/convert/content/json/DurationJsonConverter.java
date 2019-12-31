package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.content.ContentConverter;
import guru.bug.austras.convert.content.plaintext.PlainText;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

import java.time.Duration;

@ApplicationJson
public class DurationJsonConverter extends AbstractJsonConverter<Duration> {
    private final ContentConverter<Duration> converter;

    @SuppressWarnings("WeakerAccess")
    public DurationJsonConverter(@PlainText ContentConverter<Duration> converter) {
        this.converter = converter;
    }

    @Override
    public void toJson(Duration value, JsonValueWriter writer) {
        var str = converter.toString(value);
        writer.writeString(str);
    }

    @Override
    public Duration fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return converter.fromString(str);
    }
}
