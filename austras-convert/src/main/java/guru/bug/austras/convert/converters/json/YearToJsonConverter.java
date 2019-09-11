package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;

import java.time.Year;

public class YearToJsonConverter implements JsonConverter<Year> {
    private final StringConverter<Year> stringConverter;

    public YearToJsonConverter(StringConverter<Year> stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public void toJson(Year value, JsonValueWriter writer) {
        var str = stringConverter.toString(value);
        writer.write(str);
    }

    @Override
    public Year fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return stringConverter.fromString(str);
    }

}
