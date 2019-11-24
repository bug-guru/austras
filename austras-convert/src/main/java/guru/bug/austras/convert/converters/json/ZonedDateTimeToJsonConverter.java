package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.core.Component;

import java.time.ZonedDateTime;

@Component
public class ZonedDateTimeToJsonConverter implements JsonConverter<ZonedDateTime> {
    private final StringConverter<ZonedDateTime> stringConverter;

    @SuppressWarnings("WeakerAccess")
    public ZonedDateTimeToJsonConverter(StringConverter<ZonedDateTime> stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public void toJson(ZonedDateTime value, JsonValueWriter writer) {
        var str = stringConverter.toString(value);
        writer.writeString(str);
    }

    @Override
    public ZonedDateTime fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return stringConverter.fromString(str);
    }

}
