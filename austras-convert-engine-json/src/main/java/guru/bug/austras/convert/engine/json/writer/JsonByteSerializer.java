package guru.bug.austras.convert.engine.json.writer;

public interface JsonByteSerializer {
    void toJson(byte value, JsonValueWriter writer);
}
