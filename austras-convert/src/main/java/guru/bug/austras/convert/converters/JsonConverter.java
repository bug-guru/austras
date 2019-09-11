package guru.bug.austras.convert.converters;


import guru.bug.austras.convert.json.reader.JsonDeserializer;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonSerializer;
import guru.bug.austras.convert.json.writer.JsonValueWriter;

public interface JsonConverter<T> extends JsonSerializer<T>, JsonDeserializer<T> {

    @Override
    void toJson(T value, JsonValueWriter writer);

    @Override
    T fromJson(JsonValueReader reader);

}
