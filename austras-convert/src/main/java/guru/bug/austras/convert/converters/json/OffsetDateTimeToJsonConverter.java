package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;

import java.time.OffsetDateTime;

public class OffsetDateTimeToJsonConverter implements JsonConverter<OffsetDateTime> {
    private final StringConverter<OffsetDateTime> stringConverter;

    public OffsetDateTimeToJsonConverter(StringConverter<OffsetDateTime> stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public void toJson(OffsetDateTime value, JsonValueWriter writer) {
        var str = stringConverter.toString(value);
        writer.write(str);
    }

    @Override
    public OffsetDateTime fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return stringConverter.fromString(str);
    }

}
