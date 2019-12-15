package guru.bug.austras.meta;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class QualifierSetMetaInfo {
    private final Map<String, QualifierMetaInfo> qualifiers;

    private QualifierSetMetaInfo(Map<String, QualifierMetaInfo> qualifiers) {
        this.qualifiers = qualifiers == null ? Map.of() : Map.copyOf(qualifiers);
    }

    public static Builder build() {
        return new Builder();
    }

    public boolean hasQualifier(String name) {
        return qualifiers.containsKey(name);
    }

    public QualifierMetaInfo getQualifier(String name) {
        return qualifiers.get(name);
    }

    public static class Builder {
        private final Map<String, QualifierMetaInfo> qualifiers = new HashMap<>();

        private Builder() {
        }

        public Builder add(String qualifierName) {
            qualifiers.put(qualifierName, QualifierMetaInfo.empty());
            return this;
        }

        public Builder add(String qualifierName, QualifierMetaInfo props) {
            qualifiers.put(qualifierName, props);
            return this;
        }

        public Builder add(String qualifierName, Consumer<QualifierMetaInfo.Builder> builderConsumer) {
            var propBuilder = QualifierMetaInfo.builder();
            builderConsumer.accept(propBuilder);
            add(qualifierName, propBuilder.build());
            return this;
        }

        public QualifierSetMetaInfo build() {
            return new QualifierSetMetaInfo(qualifiers);
        }
    }
}
