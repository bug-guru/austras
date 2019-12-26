package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.engine.json.JsonConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.YearMonth;

@Default
public class YearMonthToJsonConverter implements JsonConverter<YearMonth> {
    private final StringConverter<YearMonth> stringConverter;

    @SuppressWarnings("WeakerAccess")
    public YearMonthToJsonConverter(StringConverter<YearMonth> stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public void toJson(YearMonth value, JsonValueWriter writer) {
        var str = stringConverter.toString(value);
        writer.writeString(str);
    }

    @Override
    public YearMonth fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return stringConverter.fromString(str);
    }

}
