/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.core.qualifiers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class QualifierSetInfo {
    private static final QualifierSetInfo DEFAULT = new QualifierSetInfo(Map.of("austras.default", QualifierInfo.empty()));
    private final Map<String, QualifierInfo> qualifiers;

    private QualifierSetInfo(Map<String, QualifierInfo> qualifiers) {
        this.qualifiers = qualifiers == null ? Map.of() : Map.copyOf(qualifiers);
    }

    public static QualifierSetInfo defaultQualifier() {
        return DEFAULT;
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean hasQualifier(String name) {
        return qualifiers.containsKey(name);
    }

    public Optional<QualifierInfo> getQualifier(String name) {
        return Optional.ofNullable(qualifiers.get(name));
    }

    public static class Builder {
        private final Map<String, QualifierInfo> qualifiers = new HashMap<>();

        private Builder() {
        }

        public Builder add(String qualifierName) {
            qualifiers.put(qualifierName, QualifierInfo.empty());
            return this;
        }

        public Builder add(String qualifierName, QualifierInfo props) {
            qualifiers.put(qualifierName, props);
            return this;
        }

        public Builder add(String qualifierName, Consumer<QualifierInfo.Builder> builderConsumer) {
            var propBuilder = QualifierInfo.builder();
            builderConsumer.accept(propBuilder);
            add(qualifierName, propBuilder.build());
            return this;
        }

        public QualifierSetInfo build() {
            return new QualifierSetInfo(qualifiers);
        }
    }
}
