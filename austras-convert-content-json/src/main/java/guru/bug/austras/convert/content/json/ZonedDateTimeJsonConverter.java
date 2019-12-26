package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.content.ContentConverter;
import guru.bug.austras.convert.content.plaintext.PlainText;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

import java.time.ZonedDateTime;

@ApplicationJson
public class ZonedDateTimeJsonConverter extends AbstractJsonConverter<ZonedDateTime> {
    private final ContentConverter<ZonedDateTime> converter;

    @SuppressWarnings("WeakerAccess")
    public ZonedDateTimeJsonConverter(@PlainText ContentConverter<ZonedDateTime> converter) {
        this.converter = converter;
    }

    @Override
    public void toJson(ZonedDateTime value, JsonValueWriter writer) {
        var str = converter.toString(value);
        writer.writeString(str);
    }

    @Override
    public ZonedDateTime fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return converter.fromString(str);
    }

}
