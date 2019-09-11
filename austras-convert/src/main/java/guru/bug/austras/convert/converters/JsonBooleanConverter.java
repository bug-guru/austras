package guru.bug.austras.convert.converters;


import guru.bug.austras.convert.json.reader.JsonBooleanDeserializer;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonBooleanSerializer;
import guru.bug.austras.convert.json.writer.JsonValueWriter;

public interface JsonBooleanConverter extends JsonBooleanSerializer, JsonBooleanDeserializer {

    @Override
    void toJson(boolean value, JsonValueWriter writer);

    @Override
    boolean fromJson(JsonValueReader reader);

}
