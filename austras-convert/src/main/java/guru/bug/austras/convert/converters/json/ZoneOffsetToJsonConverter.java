package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

import java.time.ZoneOffset;

@Default
public class ZoneOffsetToJsonConverter implements JsonConverter<ZoneOffset> {
    private final StringConverter<ZoneOffset> stringConverter;

    @SuppressWarnings("WeakerAccess")
    public ZoneOffsetToJsonConverter(StringConverter<ZoneOffset> stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public void toJson(ZoneOffset value, JsonValueWriter writer) {
        var str = stringConverter.toString(value);
        writer.writeString(str);
    }

    @Override
    public ZoneOffset fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return stringConverter.fromString(str);
    }

}
