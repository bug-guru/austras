package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.engine.json.JsonConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.Duration;

@Default
public class DurationToJsonConverter implements JsonConverter<Duration> {
    private final StringConverter<Duration> stringConverter;

    @SuppressWarnings("WeakerAccess")
    public DurationToJsonConverter(StringConverter<Duration> stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public void toJson(Duration value, JsonValueWriter writer) {
        var str = stringConverter.toString(value);
        writer.writeString(str);
    }

    @Override
    public Duration fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return stringConverter.fromString(str);
    }
}
