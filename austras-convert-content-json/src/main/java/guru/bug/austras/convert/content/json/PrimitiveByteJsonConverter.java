package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.content.ByteContentConverter;
import guru.bug.austras.convert.engine.json.JsonByteConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

@ApplicationJson
public class PrimitiveByteJsonConverter implements ByteContentConverter, JsonByteConverter {
    @Override
    public void toJson(byte value, JsonValueWriter writer) {
        writer.writeByte(value);
    }

    @Override
    public byte fromJson(JsonValueReader reader) {
        return reader.readByte();
    }

    @Override
    public byte fromString(String value) {
        return read(new StringReader(value));
    }

    @Override
    public String toString(byte value) {
        var result = new StringWriter(10);
        write(value, result);
        return result.toString();
    }

    @Override
    public byte read(Reader reader) {
        var jsonReader = JsonValueReader.newInstance(reader);
        return fromJson(jsonReader);
    }

    @Override
    public void write(byte value, Writer writer) {
        var jsonWriter = JsonValueWriter.newInstance(writer);
        toJson(value, jsonWriter);
    }
}