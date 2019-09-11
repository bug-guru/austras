package guru.bug.austras.convert.json.writer;

public interface JsonFloatSerializer {
    void toJson(float value, JsonValueWriter writer);
}
