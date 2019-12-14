package guru.bug.austras.meta;

import java.util.Map;

public class QualifierMetaInfo {
    private final String name;
    private final Map<String, String> properties;

    public QualifierMetaInfo(String name, Map<String, String> properties) {
        this.name = name;
        this.properties = properties == null ? Map.of() : Map.copyOf(properties);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

    }
}
