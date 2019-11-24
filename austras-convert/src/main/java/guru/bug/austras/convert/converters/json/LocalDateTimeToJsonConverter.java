package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.core.Component;

import java.time.LocalDateTime;

@Component
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
