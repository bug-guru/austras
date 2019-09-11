package guru.bug.austras.convert.json.writer;

public interface JsonShortSerializer {
    void toJson(short value, JsonValueWriter writer);
}
