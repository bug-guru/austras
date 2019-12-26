package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

@ApplicationJson
public class LongJsonConverter extends AbstractJsonConverter<Long> {
    @Override
    public void toJson(Long value, JsonValueWriter writer) {
        writer.writeLong(value);
    }

    @Override
    public Long fromJson(JsonValueReader reader) {
        return reader.readNullableLong();
    }

}
