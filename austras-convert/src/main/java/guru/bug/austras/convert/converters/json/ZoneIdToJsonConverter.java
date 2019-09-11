package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;

import java.time.ZoneId;

public class ZoneIdToJsonConverter implements JsonConverter<ZoneId> {
    private final StringConverter<ZoneId> stringConverter;

    public ZoneIdToJsonConverter(StringConverter<ZoneId> stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public void toJson(ZoneId value, JsonValueWriter writer) {
        var str = stringConverter.toString(value);
        writer.write(str);
    }

    @Override
    public ZoneId fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return stringConverter.fromString(str);
    }

}
