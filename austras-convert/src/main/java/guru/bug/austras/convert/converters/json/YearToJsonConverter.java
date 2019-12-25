package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

import java.time.Year;

@Default
public class YearToJsonConverter implements JsonConverter<Year> {
    private final StringConverter<Year> stringConverter;

    @SuppressWarnings("WeakerAccess")
    public YearToJsonConverter(StringConverter<Year> stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public void toJson(Year value, JsonValueWriter writer) {
        var str = stringConverter.toString(value);
        writer.writeString(str);
    }

    @Override
    public Year fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return stringConverter.fromString(str);
    }

}
