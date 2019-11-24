package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.core.Component;

import java.util.UUID;

@Component
public class UUIDToJsonConverter implements JsonConverter<UUID> {
    private final StringConverter<UUID> stringConverter;

    @SuppressWarnings("WeakerAccess")
    public UUIDToJsonConverter(StringConverter<UUID> stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public void toJson(UUID value, JsonValueWriter writer) {
        var str = stringConverter.toString(value);
        writer.writeString(str);
    }

    @Override
    public UUID fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return stringConverter.fromString(str);
    }

}
