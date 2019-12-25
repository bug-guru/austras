package guru.bug.austras.web.contentconverter.json;

import guru.bug.austras.json.JsonByteConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;
import guru.bug.austras.web.MediaType;
import guru.bug.austras.web.contentconverter.ByteContentConverter;
import guru.bug.austras.web.contentconverter.Converts;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

@Converts(type = MediaType.APPLICATION_JSON)
public class JsonByteContentConverter implements ByteContentConverter {
    private final JsonByteConverter byteConverter;

    public JsonByteContentConverter(JsonByteConverter byteConverter) {
        this.byteConverter = byteConverter;
    }

    @Override
    public byte fromString(String value) {
        return byteConverter.fromJson(JsonValueReader.newInstance(new StringReader(value)));
    }

    @Override
    public String toString(byte value) {
        var result = new StringWriter(10);
        byteConverter.toJson(value, JsonValueWriter.newInstance(result));
        return result.toString();
    }

    @Override
    public byte read(Reader reader) {
        return byteConverter.fromJson(JsonValueReader.newInstance(reader));
    }

    @Override
    public void write(byte value, Writer writer) {
        byteConverter.toJson(value, JsonValueWriter.newInstance(writer));
    }
}

