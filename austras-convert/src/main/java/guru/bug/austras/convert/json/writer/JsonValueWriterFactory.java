package guru.bug.austras.convert.json.writer;

import java.io.Writer;

public class JsonValueWriterFactory {

    public JsonValueWriter newInstance(Writer out) {
        var tokenWriter = new JsonTokenWriter(out);
        return new JsonValueWriterImpl(tokenWriter);
    }

}
