package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.content.ContentConverter;
import guru.bug.austras.convert.content.plaintext.PlainText;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

import java.time.LocalDate;

@ApplicationJson
public class LocalDateJsonConverter extends AbstractJsonConverter<LocalDate> {
    private final ContentConverter<LocalDate> converter;

    @SuppressWarnings("WeakerAccess")
    public LocalDateJsonConverter(@PlainText ContentConverter<LocalDate> converter) {
        this.converter = converter;
    }

    @Override
    public void toJson(LocalDate value, JsonValueWriter writer) {
        var str = converter.toString(value);
        writer.writeString(str);
    }

    @Override
    public LocalDate fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return converter.fromString(str);
    }
}
