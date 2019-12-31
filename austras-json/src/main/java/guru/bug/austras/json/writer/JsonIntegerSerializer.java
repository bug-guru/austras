package guru.bug.austras.json.writer;

public interface JsonIntegerSerializer {
    void toJson(int value, JsonValueWriter writer);
}
