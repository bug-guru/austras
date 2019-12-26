package guru.bug.austras.web.contentconverter.json;

import guru.bug.austras.convert.engine.json.JsonLongConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;
import guru.bug.austras.web.MediaType;
import guru.bug.austras.web.contentconverter.Converts;
import guru.bug.austras.web.contentconverter.LongContentConverter;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

@Converts(type = MediaType.APPLICATION_JSON)
public class JsonLongContentConverter implements LongContentConverter {
    private final JsonLongConverter longConverter;

    public JsonLongContentConverter(JsonLongConverter longConverter) {
        this.longConverter = longConverter;
    }

    @Override
    public long fromString(String value) {
        return longConverter.fromJson(JsonValueReader.newInstance(new StringReader(value)));
    }

    @Override
    public String toString(long value) {
        var result = new StringWriter(10);
        longConverter.toJson(value, JsonValueWriter.newInstance(result));
        return result.toString();
    }

    @Override
    public long read(Reader reader) {
        return longConverter.fromJson(JsonValueReader.newInstance(reader));
    }

    @Override
    public void write(long value, Writer writer) {
        longConverter.toJson(value, JsonValueWriter.newInstance(writer));
    }
}

