package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.core.Component;

import java.time.ZoneOffset;

@Component
public class ZoneOffsetToJsonConverter implements JsonConverter<ZoneOffset> {
    private final StringConverter<ZoneOffset> stringConverter;

    public ZoneOffsetToJsonConverter(StringConverter<ZoneOffset> stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public void toJson(ZoneOffset value, JsonValueWriter writer) {
        var str = stringConverter.toString(value);
        writer.write(str);
    }

    @Override
    public ZoneOffset fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return stringConverter.fromString(str);
    }

}
