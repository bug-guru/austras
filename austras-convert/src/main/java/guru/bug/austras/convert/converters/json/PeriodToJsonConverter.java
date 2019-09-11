package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;

import java.time.Period;

public class PeriodToJsonConverter implements JsonConverter<Period> {
    private final StringConverter<Period> stringConverter;

    public PeriodToJsonConverter(StringConverter<Period> stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public void toJson(Period value, JsonValueWriter writer) {
        var str = stringConverter.toString(value);
        writer.write(str);
    }

    @Override
    public Period fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return stringConverter.fromString(str);
    }

}
