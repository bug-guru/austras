package guru.bug.austras.core.qualifiers;

import java.util.HashMap;
import java.util.Map;

public class QualifierInfo {
    private static final QualifierInfo EMPTY = new QualifierInfo(null);
    private final Map<String, String> properties;

    private QualifierInfo(Map<String, String> properties) {
        this.properties = properties == null ? Map.of() : Map.copyOf(properties);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static QualifierInfo empty() {
        return EMPTY;
    }

    public boolean hasProperty(String key) {
        return properties.containsKey(key);
    }

    public boolean hasProperty(String key, String value) {
        var v = properties.get(key);
        if (v == null) {
            return false;
        }
        return v.equals(value);
    }

    public String getValue(String key) {
        return properties.get(key);
    }

    public static class Builder {
        private final Map<String, String> properties = new HashMap<>();

        private Builder() {
        }

        public Builder add(String key, String value) {
            properties.put(key, value);
            return this;
        }

        public QualifierInfo build() {
            return new QualifierInfo(properties);
        }
    }
}