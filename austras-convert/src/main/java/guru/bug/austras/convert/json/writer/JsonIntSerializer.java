package guru.bug.austras.convert.json.writer;

public interface JsonIntSerializer {
    void toJson(int value, JsonValueWriter writer);
}
