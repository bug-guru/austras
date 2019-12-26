package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.convert.engine.json.JsonConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.Period;

@Default
public class PeriodToJsonConverter implements JsonConverter<Period> {
    private final StringConverter<Period> stringConverter;

    @SuppressWarnings("WeakerAccess")
    public PeriodToJsonConverter(StringConverter<Period> stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public void toJson(Period value, JsonValueWriter writer) {
        var str = stringConverter.toString(value);
        writer.writeString(str);
    }

    @Override
    public Period fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return stringConverter.fromString(str);
    }

}
