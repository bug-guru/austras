package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.content.ContentConverter;
import guru.bug.austras.convert.content.plaintext.PlainText;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

import java.time.Month;

@ApplicationJson
public class MonthJsonConverter extends AbstractJsonConverter<Month> {
    private final ContentConverter<Month> converter;

    @SuppressWarnings("WeakerAccess")
    public MonthJsonConverter(@PlainText ContentConverter<Month> converter) {
        this.converter = converter;
    }

    @Override
    public void toJson(Month value, JsonValueWriter writer) {
        var str = converter.toString(value);
        writer.writeString(str);
    }

    @Override
    public Month fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return converter.fromString(str);
    }

}