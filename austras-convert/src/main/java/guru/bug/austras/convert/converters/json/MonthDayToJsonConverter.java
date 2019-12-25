package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

import java.time.MonthDay;

@Default
public class MonthDayToJsonConverter implements JsonConverter<MonthDay> {
    private final StringConverter<MonthDay> stringConverter;

    @SuppressWarnings("WeakerAccess")
    public MonthDayToJsonConverter(StringConverter<MonthDay> stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public void toJson(MonthDay value, JsonValueWriter writer) {
        var str = stringConverter.toString(value);
        writer.writeString(str);
    }

    @Override
    public MonthDay fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return stringConverter.fromString(str);
    }

}
