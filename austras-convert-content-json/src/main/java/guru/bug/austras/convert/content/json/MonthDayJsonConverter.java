package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.content.ContentConverter;
import guru.bug.austras.convert.content.plaintext.PlainText;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

import java.time.MonthDay;

@ApplicationJson
public class MonthDayJsonConverter extends AbstractJsonConverter<MonthDay> {
    private final ContentConverter<MonthDay> converter;

    @SuppressWarnings("WeakerAccess")
    public MonthDayJsonConverter(@PlainText ContentConverter<MonthDay> converter) {
        this.converter = converter;
    }

    @Override
    public void toJson(MonthDay value, JsonValueWriter writer) {
        var str = converter.toString(value);
        writer.writeString(str);
    }

    @Override
    public MonthDay fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return converter.fromString(str);
    }

}
