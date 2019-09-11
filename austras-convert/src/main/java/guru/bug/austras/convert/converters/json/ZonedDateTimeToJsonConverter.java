package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;

import java.time.ZonedDateTime;

public class ZonedDateTimeToJsonConverter implements JsonConverter<ZonedDateTime> {
    private final StringConverter<ZonedDateTime> stringConverter;

    public ZonedDateTimeToJsonConverter(StringConverter<ZonedDateTime> stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public void toJson(ZonedDateTime value, JsonValueWriter writer) {
        var str = stringConverter.toString(value);
        writer.write(str);
    }

    @Override
    public ZonedDateTime fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return stringConverter.fromString(str);
    }

}
