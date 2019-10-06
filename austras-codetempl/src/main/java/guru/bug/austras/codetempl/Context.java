package guru.bug.austras.codetempl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Context {
    private static final Pattern NAME_SPLIT = Pattern.compile("\\.");
    private final Context parent;
    private final Map<String, Value> values;

    private Context(Context parent, Map<String, Value> values) {
        this.parent = parent;
        this.values = values;
    }

    public static Builder builder() {
        return new Builder(null);
    }

    public Value get(String name) {
        var parts = List.of(NAME_SPLIT.split(name));
        return get(parts);
    }

    private Value get(List<String> parts) {
        var partsIter = parts.iterator();
        if (!partsIter.hasNext()) {
            return null;
        }
        var value = values.get(partsIter.next());
        if (value == null) {
            return parent == null ? null : parent.get(parts);
        }

        while (partsIter.hasNext() && value != null) {
            value = value.getValueAt(partsIter.next());
        }

        return value;
    }

    public Builder childBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private final Context parent;
        private final HashMap<String, Value> values = new HashMap<>();

        private Builder(Context parent) {
            this.parent = parent;
        }

        public Builder put(String name, Value value) {
            Value.validateName(name);
            values.put(name, value);
            return this;
        }


        public Context build() {
            return new Context(parent, values);
        }
    }
}
