package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.core.Component;

import java.time.OffsetTime;

@Component
public class OffsetTimeToJsonConverter implements JsonConverter<OffsetTime> {
    private final StringConverter<OffsetTime> stringConverter;

    public OffsetTimeToJsonConverter(StringConverter<OffsetTime> stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public void toJson(OffsetTime value, JsonValueWriter writer) {
        var str = stringConverter.toString(value);
        writer.write(str);
    }

    @Override
    public OffsetTime fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return stringConverter.fromString(str);
    }

}
