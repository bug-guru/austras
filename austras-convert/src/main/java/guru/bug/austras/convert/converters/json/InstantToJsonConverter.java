package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.engine.json.JsonConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.Instant;

@Default
public class InstantToJsonConverter implements JsonConverter<Instant> {
    private final StringConverter<Instant> stringConverter;

    @SuppressWarnings("WeakerAccess")
    public InstantToJsonConverter(StringConverter<Instant> stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public void toJson(Instant value, JsonValueWriter writer) {
        var str = stringConverter.toString(value);
        writer.writeString(str);
    }

    @Override
    public Instant fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return stringConverter.fromString(str);
    }
}
