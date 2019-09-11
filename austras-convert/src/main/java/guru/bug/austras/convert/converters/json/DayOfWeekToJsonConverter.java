package guru.bug.austras.convert.converters.json;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;

import java.time.DayOfWeek;

public class DayOfWeekToJsonConverter implements JsonConverter<DayOfWeek> {
    private final StringConverter<DayOfWeek> stringConverter;

    public DayOfWeekToJsonConverter(StringConverter<DayOfWeek> stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public void toJson(DayOfWeek value, JsonValueWriter writer) {
        var str = stringConverter.toString(value);
        writer.write(str);
    }

    @Override
    public DayOfWeek fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return stringConverter.fromString(str);
    }
}
