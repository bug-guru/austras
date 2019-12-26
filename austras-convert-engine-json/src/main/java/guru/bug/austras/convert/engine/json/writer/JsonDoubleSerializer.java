package guru.bug.austras.convert.engine.json.writer;

public interface JsonDoubleSerializer {
    void toJson(double value, JsonValueWriter writer);
}
