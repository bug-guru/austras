package guru.bug.austras.convert.json.writer;

public interface JsonIntegerSerializer {
    void toJson(int value, JsonValueWriter writer);
}
