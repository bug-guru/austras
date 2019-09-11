package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;

import java.time.Duration;

public class DurationToJsonConverter implements JsonConverter<Duration> {
    private final StringConverter<Duration> stringConverter;

    public DurationToJsonConverter(StringConverter<Duration> stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public void toJson(Duration value, JsonValueWriter writer) {
        var str = stringConverter.toString(value);
        writer.write(str);
    }

    @Override
    public Duration fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return stringConverter.fromString(str);
    }
}
