package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.engine.json.JsonConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.LocalDate;

@Default
public class LocalDateToJsonConverter implements JsonConverter<LocalDate> {
    private final StringConverter<LocalDate> stringConverter;

    @SuppressWarnings("WeakerAccess")
    public LocalDateToJsonConverter(StringConverter<LocalDate> stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public void toJson(LocalDate value, JsonValueWriter writer) {
        var str = stringConverter.toString(value);
        writer.writeString(str);
    }

    @Override
    public LocalDate fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return stringConverter.fromString(str);
    }
}
