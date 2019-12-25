package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.Month;

@Default
public class MonthToJsonConverter implements JsonConverter<Month> {
    private final StringConverter<Month> stringConverter;

    @SuppressWarnings("WeakerAccess")
    public MonthToJsonConverter(StringConverter<Month> stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public void toJson(Month value, JsonValueWriter writer) {
        var str = stringConverter.toString(value);
        writer.writeString(str);
    }

    @Override
    public Month fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return stringConverter.fromString(str);
    }

}
