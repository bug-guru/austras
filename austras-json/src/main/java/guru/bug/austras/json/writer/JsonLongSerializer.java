package guru.bug.austras.json.writer;

public interface JsonLongSerializer {
    void toJson(long value, JsonValueWriter writer);
}
