package guru.bug.austras.convert.json.writer;

public interface JsonByteSerializer {
    void toJson(byte value, JsonValueWriter writer);
}
