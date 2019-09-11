package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;

import java.time.Month;

public class MonthToJsonConverter implements JsonConverter<Month> {
    private final StringConverter<Month> stringConverter;

    public MonthToJsonConverter(StringConverter<Month> stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public void toJson(Month value, JsonValueWriter writer) {
        var str = stringConverter.toString(value);
        writer.write(str);
    }

    @Override
    public Month fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return stringConverter.fromString(str);
    }

}
