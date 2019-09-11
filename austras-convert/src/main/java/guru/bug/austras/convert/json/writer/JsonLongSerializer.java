package guru.bug.austras.convert.json.writer;

public interface JsonLongSerializer {
    void toJson(long value, JsonValueWriter writer);
}
