package guru.bug.austras.convert.engine.json.writer;

public interface JsonFloatSerializer {
    void toJson(float value, JsonValueWriter writer);
}
