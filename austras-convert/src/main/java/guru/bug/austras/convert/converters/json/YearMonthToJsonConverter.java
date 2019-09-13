package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.core.Component;

import java.time.YearMonth;

@Component
public class YearMonthToJsonConverter implements JsonConverter<YearMonth> {
    private final StringConverter<YearMonth> stringConverter;

    public YearMonthToJsonConverter(StringConverter<YearMonth> stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public void toJson(YearMonth value, JsonValueWriter writer) {
        var str = stringConverter.toString(value);
        writer.write(str);
    }

    @Override
    public YearMonth fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return stringConverter.fromString(str);
    }

}
