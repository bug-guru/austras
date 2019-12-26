package guru.bug.austras.web.contentconverter.json;

import guru.bug.austras.convert.engine.json.JsonShortConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;
import guru.bug.austras.web.MediaType;
import guru.bug.austras.web.contentconverter.Converts;
import guru.bug.austras.web.contentconverter.ShortContentConverter;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

@Converts(type = MediaType.APPLICATION_JSON)
public class JsonShortContentConverter implements ShortContentConverter {
    private final JsonShortConverter shortConverter;

    public JsonShortContentConverter(JsonShortConverter shortConverter) {
        this.shortConverter = shortConverter;
    }

    @Override
    public short fromString(String value) {
        return shortConverter.fromJson(JsonValueReader.newInstance(new StringReader(value)));
    }

    @Override
    public String toString(short value) {
        var result = new StringWriter(10);
        shortConverter.toJson(value, JsonValueWriter.newInstance(result));
        return result.toString();
    }

    @Override
    public short read(Reader reader) {
        return shortConverter.fromJson(JsonValueReader.newInstance(reader));
    }

    @Override
    public void write(short value, Writer writer) {
        shortConverter.toJson(value, JsonValueWriter.newInstance(writer));
    }
}

