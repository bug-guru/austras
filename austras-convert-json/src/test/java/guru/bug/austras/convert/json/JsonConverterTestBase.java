package guru.bug.austras.convert.json;

import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.function.Consumer;

abstract class JsonConverterTestBase {

    String toJson(Consumer<JsonValueWriter> writerConsumer) {
        var out = new StringWriter();
        var writer = JsonValueWriter.newInstance(out);
        writerConsumer.accept(writer);
        return out.toString();
    }

    JsonValueReader reader(String json) {
        var in = new StringReader(json);
        return JsonValueReader.newInstance(in);
    }
}
