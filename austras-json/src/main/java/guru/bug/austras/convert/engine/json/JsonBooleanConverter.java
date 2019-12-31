package guru.bug.austras.convert.engine.json;


import guru.bug.austras.convert.engine.json.reader.JsonBooleanDeserializer;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonBooleanSerializer;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

public interface JsonBooleanConverter extends JsonBooleanSerializer, JsonBooleanDeserializer {

    @Override
    void toJson(boolean value, JsonValueWriter writer);

    @Override
    boolean fromJson(JsonValueReader reader);

}
