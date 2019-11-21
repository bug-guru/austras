package guru.bug.austras.convert.json.reader;

import java.io.Reader;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class JsonValueReaderFactory {

    public JsonValueReader newInstance(Reader reader) {
        var tokenReader = new JsonTokenReader(reader);
        return new JsonValueReaderImpl(tokenReader);
    }
}
