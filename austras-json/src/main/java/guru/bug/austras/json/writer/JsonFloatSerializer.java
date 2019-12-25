package guru.bug.austras.json.writer;

public interface JsonFloatSerializer {
    void toJson(float value, JsonValueWriter writer);
}
