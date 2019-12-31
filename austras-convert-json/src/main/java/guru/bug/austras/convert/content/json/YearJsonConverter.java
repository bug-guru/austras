package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.content.ContentConverter;
import guru.bug.austras.convert.content.plaintext.PlainText;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

import java.time.Year;

@ApplicationJson
public class YearJsonConverter extends AbstractJsonConverter<Year> {
    private final ContentConverter<Year> converter;

    @SuppressWarnings("WeakerAccess")
    public YearJsonConverter(@PlainText ContentConverter<Year> converter) {
        this.converter = converter;
    }

    @Override
    public void toJson(Year value, JsonValueWriter writer) {
        var str = converter.toString(value);
        writer.writeString(str);
    }

    @Override
    public Year fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return converter.fromString(str);
    }

}
