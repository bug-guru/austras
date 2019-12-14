package guru.bug.austras.meta;

import java.util.HashMap;
import java.util.Map;

public class QualifierMetaInfo {
    private final String name;
    private final Map<String, String> properties;

    private QualifierMetaInfo(String name, Map<String, String> properties) {
        this.name = name;
        this.properties = properties == null ? Map.of() : Map.copyOf(properties);
    }

    public static Builder builderFor(String name) {
        return new Builder(name);
    }


    public static class Builder {
        private final String name;
        private final Map<String, String> properties = new HashMap<>();

        private Builder(String name) {
            this.name = name;
        }

        public Builder property(String key, String value) {
            properties.put(key, value);
            return this;
        }

        public QualifierMetaInfo build() {
            return new QualifierMetaInfo(name, properties);
        }
    }
}
