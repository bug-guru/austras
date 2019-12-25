package guru.bug.austras.convert.converters.json;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonBooleanConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

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
