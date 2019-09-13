package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.core.Component;

import java.time.LocalTime;

@Component
public class LocalTimeToJsonConverter implements JsonConverter<LocalTime> {
    private final StringConverter<LocalTime> stringConverter;

    public LocalTimeToJsonConverter(StringConverter<LocalTime> stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public void toJson(LocalTime value, JsonValueWriter writer) {
        var str = stringConverter.toString(value);
        writer.write(str);
    }

    @Override
    public LocalTime fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return stringConverter.fromString(str);
    }
}
