package guru.bug.austras.json.writer;

public interface JsonShortSerializer {
    void toJson(short value, JsonValueWriter writer);
}
