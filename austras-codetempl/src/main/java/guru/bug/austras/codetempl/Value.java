package guru.bug.austras.codetempl;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class Value implements Printable {
    public static final Value EMPTY = new Value(null, null);
    private static final Pattern VALID_NAME = Pattern.compile("[A-Z][_A-Z0-9]*");
    private final Map<String, Value> children;
    private final String value;

    private Value(String value, Map<String, Value> children) {
        this.value = value;
        this.children = children == null ? Map.of() : Map.copyOf(children);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Value of(int value) {
        return Value.builder().set(value).build();
    }

    public static Value of(String value) {
        return Value.builder().set(value).build();
    }

    public static Value of(boolean value) {
        return Value.builder().set(value).build();
    }

    public static void validateName(String name) {
        if (!VALID_NAME.matcher(name).matches()) {
            throw new IllegalArgumentException("Variable name " + name + " isn't valid. Only uppercase SNAKE_CASE is allowed.");
        }
    }

    public Value getValueAt(String key) {
        return children.getOrDefault(key, Value.EMPTY);
    }

    public Value getValueAt(int index) {
        var size = getValueAt("SIZE").getInteger();
        if (children.isEmpty() || size == null || index < 0 || index >= size) {
            return Value.EMPTY;
        }
        return children.get(Integer.toString(index));
    }

    public String getString() {
        return value;
    }

    public Integer getInteger() {
        if (value == null) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public boolean getBoolean() {
        if (value == null) {
            return false;
        }
        return Boolean.parseBoolean(value);
    }

    @Override
    public void print(PrintWriter out) {
        if (value == null) {
            return;
        }
        out.print(value);
    }

    public static class Builder {
        private String value;
        private Map<String, Value> children;

        private Builder() {

        }

        public Builder set(String value) {
            this.value = value;
            return this;
        }

        public Builder set(int value) {
            this.value = Integer.toString(value);
            return this;
        }

        public Builder set(boolean value) {
            this.value = Boolean.toString(value);
            return this;
        }

        public Builder array(Consumer<ArrayBuilder> arrayBuilderConsumer) {
            var ab = new ArrayBuilder();
            arrayBuilderConsumer.accept(ab);
            this.children = ab.build();
            return this;
        }

        public Builder map(Consumer<MapBuilder> mapBuilderConsumer) {
            var mb = new MapBuilder();
            mapBuilderConsumer.accept(mb);
            this.children = mb.build();
            return this;
        }

        public Value build() {
            return new Value(value, children);
        }
    }

    public static class ArrayBuilder {
        private final List<Value> values = new ArrayList<>();

        private ArrayBuilder() {

        }

        public ArrayBuilder add(Value value) {
            values.add(value);
            return this;
        }

        private Map<String, Value> build() {
            var result = new HashMap<String, Value>();
            result.put("SIZE", Value.of(values.size()));
            result.put("IS_EMPTY", Value.of(values.isEmpty()));
            result.put("IS_NOT_EMPTY", Value.of(!values.isEmpty()));
            for (int i = 0; i < values.size(); i++) {
                var value = values.get(i);
                result.put(Integer.toString(i), value);
            }
            return result;
        }

    }

    public static class MapBuilder {
        private final Map<String, Value> values = new HashMap<>();

        private MapBuilder() {

        }

        public MapBuilder put(String key, Value value) {
            Value.validateName(key);
            values.put(key, value);
            return this;
        }

        private Map<String, Value> build() {
            return values;
        }

    }

}
