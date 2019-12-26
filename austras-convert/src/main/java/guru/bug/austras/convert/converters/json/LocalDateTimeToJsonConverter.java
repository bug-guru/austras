package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.engine.json.JsonConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.LocalDateTime;

@Default
public class LocalDateTimeToJsonConverter implements JsonConverter<LocalDateTime> {
    private final StringConverter<LocalDateTime> stringConverter;

    @SuppressWarnings("WeakerAccess")
    public LocalDateTimeToJsonConverter(StringConverter<LocalDateTime> stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public void toJson(LocalDateTime value, JsonValueWriter writer) {
        var str = stringConverter.toString(value);
        writer.writeString(str);
    }

    @Override
    public LocalDateTime fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return stringConverter.fromString(str);
    }
}
