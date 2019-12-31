package guru.bug.austras.convert.json;

import guru.bug.austras.convert.ContentConverter;
import guru.bug.austras.json.JsonConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public abstract class AbstractJsonConverter<T> implements ContentConverter<T>, JsonConverter<T> {

    @Override
    public T fromString(String value) {
        return read(new StringReader(value));
    }

    @Override
    public String toString(T value) {
        var result = new StringWriter(10);
        write(value, result);
        return result.toString();
    }

    @Override
    public T read(Reader reader) {
        var jsonReader = JsonValueReader.newInstance(reader);
        return fromJson(jsonReader);
    }

    @Override
    public void write(T value, Writer writer) {
        var jsonWriter = JsonValueWriter.newInstance(writer);
        toJson(value, jsonWriter);
    }
}
