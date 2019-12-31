package guru.bug.austras.convert.json;

import guru.bug.austras.convert.ContentConverter;
import guru.bug.austras.convert.plaintext.PlainText;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

import java.time.YearMonth;

@ApplicationJson
public class YearMonthJsonConverter extends AbstractJsonConverter<YearMonth> {
    private final ContentConverter<YearMonth> converter;

    @SuppressWarnings("WeakerAccess")
    public YearMonthJsonConverter(@PlainText ContentConverter<YearMonth> converter) {
        this.converter = converter;
    }

    @Override
    public void toJson(YearMonth value, JsonValueWriter writer) {
        var str = converter.toString(value);
        writer.writeString(str);
    }

    @Override
    public YearMonth fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return converter.fromString(str);
    }

}
