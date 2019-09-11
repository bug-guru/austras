package guru.bug.austras.convert.json.writer;

public interface JsonCharSerializer {
    void toJson(char value, JsonValueWriter writer);
}
