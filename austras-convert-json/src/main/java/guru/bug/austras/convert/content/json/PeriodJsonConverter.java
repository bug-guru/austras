package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.content.ContentConverter;
import guru.bug.austras.convert.content.plaintext.PlainText;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

import java.time.Period;

@ApplicationJson
public class PeriodJsonConverter extends AbstractJsonConverter<Period> {
    private final ContentConverter<Period> converter;

    @SuppressWarnings("WeakerAccess")
    public PeriodJsonConverter(@PlainText ContentConverter<Period> converter) {
        this.converter = converter;
    }

    @Override
    public void toJson(Period value, JsonValueWriter writer) {
        var str = converter.toString(value);
        writer.writeString(str);
    }

    @Override
    public Period fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return converter.fromString(str);
    }

}
