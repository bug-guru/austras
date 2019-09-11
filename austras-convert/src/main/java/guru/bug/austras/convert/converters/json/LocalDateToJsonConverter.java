package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;

import java.time.LocalDate;

public class LocalDateToJsonConverter implements JsonConverter<LocalDate> {
    private final StringConverter<LocalDate> stringConverter;

    public LocalDateToJsonConverter(StringConverter<LocalDate> stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public void toJson(LocalDate value, JsonValueWriter writer) {
        var str = stringConverter.toString(value);
        writer.write(str);
    }

    @Override
    public LocalDate fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return stringConverter.fromString(str);
    }
}
