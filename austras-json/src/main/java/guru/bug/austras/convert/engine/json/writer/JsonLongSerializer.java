package guru.bug.austras.convert.engine.json.writer;

public interface JsonLongSerializer {
    void toJson(long value, JsonValueWriter writer);
}
