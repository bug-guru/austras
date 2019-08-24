package guru.bug.austras.code;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

public class TypeSpec {
    private final TypeName name;
    private final List<TypeExt> typeExts;

    private TypeSpec(TypeName name, List<TypeExt> typeExts) {
        this.name = name;
        this.typeExts = typeExts;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        var result = new StringBuilder();
        result.append(name);
        if (!typeExts.isEmpty()) {
            result.append(
                    typeExts.stream()
                            .map(TypeExt::toString)
                            .collect(joining(", ", "<", ">"))
            );
        }
        return result.toString();
    }

    public static class Builder {
        private List<TypeExt> typeExts = new ArrayList<>();
        private TypeName name;

        public Builder name(TypeName name) {
            this.name = requireNonNull(name);
            return this;
        }

        public Builder addTypeExt(TypeExt typeExt) {
            this.typeExts.add(requireNonNull(typeExt));
            return this;
        }

        public TypeSpec build() {
            return new TypeSpec(name, List.copyOf(typeExts));
        }
    }
}
