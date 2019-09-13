package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.core.Component;

@Component
public class FloatToJsonConverter implements JsonConverter<Float> {
    @Override
    public void toJson(Float value, JsonValueWriter writer) {
        writer.write(value);
    }

    @Override
    public Float fromJson(JsonValueReader reader) {
        return reader.readNullableFloat();
    }

}
