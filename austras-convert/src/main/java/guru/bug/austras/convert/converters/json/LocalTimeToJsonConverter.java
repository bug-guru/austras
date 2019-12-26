package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.engine.json.JsonConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.LocalTime;

@Default
public class LocalTimeToJsonConverter implements JsonConverter<LocalTime> {
    private final StringConverter<LocalTime> stringConverter;

    @SuppressWarnings("WeakerAccess")
    public LocalTimeToJsonConverter(StringConverter<LocalTime> stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public void toJson(LocalTime value, JsonValueWriter writer) {
        var str = stringConverter.toString(value);
        writer.writeString(str);
    }

    @Override
    public LocalTime fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return stringConverter.fromString(str);
    }
}
