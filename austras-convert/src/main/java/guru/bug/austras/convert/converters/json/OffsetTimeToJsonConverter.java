package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.engine.json.JsonConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.OffsetTime;

@Default
public class OffsetTimeToJsonConverter implements JsonConverter<OffsetTime> {
    private final StringConverter<OffsetTime> stringConverter;

    @SuppressWarnings("WeakerAccess")
    public OffsetTimeToJsonConverter(StringConverter<OffsetTime> stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public void toJson(OffsetTime value, JsonValueWriter writer) {
        var str = stringConverter.toString(value);
        writer.writeString(str);
    }

    @Override
    public OffsetTime fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return stringConverter.fromString(str);
    }

}
