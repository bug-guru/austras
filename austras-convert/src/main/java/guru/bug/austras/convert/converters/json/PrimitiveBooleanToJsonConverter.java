package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.engine.json.JsonBooleanConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;
import guru.bug.austras.core.qualifiers.Default;

@Default
public class PrimitiveBooleanToJsonConverter implements JsonBooleanConverter {
    @Override
    public void toJson(boolean value, JsonValueWriter writer) {
        writer.writeBoolean(value);
    }

    @Override
    public boolean fromJson(JsonValueReader reader) {
        return reader.readBoolean();
    }

}
