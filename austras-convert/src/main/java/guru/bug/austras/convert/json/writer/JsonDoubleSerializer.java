package guru.bug.austras.convert.json.writer;

public interface JsonDoubleSerializer {
    void toJson(double value, JsonValueWriter writer);
}
