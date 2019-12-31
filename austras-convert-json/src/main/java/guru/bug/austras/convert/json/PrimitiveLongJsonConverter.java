package guru.bug.austras.convert.json;

import guru.bug.austras.convert.LongContentConverter;
import guru.bug.austras.json.JsonLongConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

@ApplicationJson
public class PrimitiveLongJsonConverter implements LongContentConverter, JsonLongConverter {
    @Override
    public void toJson(long value, JsonValueWriter writer) {
        writer.writeLong(value);
    }

    @Override
    public long fromJson(JsonValueReader reader) {
        return reader.readLong();
    }

    @Override
    public long fromString(String value) {
        return read(new StringReader(value));
    }

    @Override
    public String toString(long value) {
        var result = new StringWriter(10);
        write(value, result);
        return result.toString();
    }

    @Override
    public long read(Reader reader) {
        var jsonReader = JsonValueReader.newInstance(reader);
        return fromJson(jsonReader);
    }

    @Override
    public void write(long value, Writer writer) {
        var jsonWriter = JsonValueWriter.newInstance(writer);
        toJson(value, jsonWriter);
    }
}
