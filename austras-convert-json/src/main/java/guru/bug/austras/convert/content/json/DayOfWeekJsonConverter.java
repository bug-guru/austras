package guru.bug.austras.convert.content.json;


import guru.bug.austras.convert.content.ContentConverter;
import guru.bug.austras.convert.content.plaintext.PlainText;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

import java.time.DayOfWeek;

@ApplicationJson
public class DayOfWeekJsonConverter extends AbstractJsonConverter<DayOfWeek> {
    private final ContentConverter<DayOfWeek> converter;

    @SuppressWarnings("WeakerAccess")
    public DayOfWeekJsonConverter(@PlainText ContentConverter<DayOfWeek> converter) {
        this.converter = converter;
    }

    @Override
    public void toJson(DayOfWeek value, JsonValueWriter writer) {
        var str = converter.toString(value);
        writer.writeString(str);
    }

    @Override
    public DayOfWeek fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return converter.fromString(str);
    }
}
