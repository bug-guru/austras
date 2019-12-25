package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.ZoneId;

@Default
public class ZoneIdToJsonConverter implements JsonConverter<ZoneId> {
    private final StringConverter<ZoneId> stringConverter;

    @SuppressWarnings("WeakerAccess")
    public ZoneIdToJsonConverter(StringConverter<ZoneId> stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public void toJson(ZoneId value, JsonValueWriter writer) {
        var str = stringConverter.toString(value);
        writer.writeString(str);
    }

    @Override
    public ZoneId fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return stringConverter.fromString(str);
    }

}
