package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.content.ContentConverter;
import guru.bug.austras.convert.content.plaintext.PlainText;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

import java.time.Instant;

@ApplicationJson
public class InstantJsonConverter extends AbstractJsonConverter<Instant> {
    private final ContentConverter<Instant> converter;

    @SuppressWarnings("WeakerAccess")
    public InstantJsonConverter(@PlainText ContentConverter<Instant> converter) {
        this.converter = converter;
    }

    @Override
    public void toJson(Instant value, JsonValueWriter writer) {
        var str = converter.toString(value);
        writer.writeString(str);
    }

    @Override
    public Instant fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return converter.fromString(str);
    }
}
