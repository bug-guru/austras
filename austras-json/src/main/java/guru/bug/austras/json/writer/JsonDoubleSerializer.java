package guru.bug.austras.json.writer;

public interface JsonDoubleSerializer {
    void toJson(double value, JsonValueWriter writer);
}
