package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;

import java.util.UUID;

public class UUIDToJsonConverter implements JsonConverter<UUID> {
    private final StringConverter<UUID> stringConverter;

    public UUIDToJsonConverter(StringConverter<UUID> stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public void toJson(UUID value, JsonValueWriter writer) {
        var str = stringConverter.toString(value);
        writer.write(str);
    }

    @Override
    public UUID fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return stringConverter.fromString(str);
    }

}
