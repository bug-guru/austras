package guru.bug.austras.convert.json;

import guru.bug.austras.convert.ShortContentConverter;
import guru.bug.austras.json.JsonShortConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

@ApplicationJson
public class PrimitiveShortJsonConverter implements ShortContentConverter, JsonShortConverter {
    @Override
    public void toJson(short value, JsonValueWriter writer) {
        writer.writeShort(value);
    }

    @Override
    public short fromJson(JsonValueReader reader) {
        return reader.readShort();
    }

    @Override
    public short fromString(String value) {
        return read(new StringReader(value));
    }

    @Override
    public String toString(short value) {
        var result = new StringWriter(10);
        write(value, result);
        return result.toString();
    }

    @Override
    public short read(Reader reader) {
        var jsonReader = JsonValueReader.newInstance(reader);
        return fromJson(jsonReader);
    }

    @Override
    public void write(short value, Writer writer) {
        var jsonWriter = JsonValueWriter.newInstance(writer);
        toJson(value, jsonWriter);
    }
}
