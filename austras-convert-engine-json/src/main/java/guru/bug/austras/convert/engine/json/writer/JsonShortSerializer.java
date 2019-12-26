package guru.bug.austras.convert.engine.json.writer;

public interface JsonShortSerializer {
    void toJson(short value, JsonValueWriter writer);
}
