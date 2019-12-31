package guru.bug.austras.json.writer;

public interface JsonByteSerializer {
    void toJson(byte value, JsonValueWriter writer);
}
