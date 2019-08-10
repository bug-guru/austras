package guru.bug.austras.apt.core.componentmap;

import guru.bug.austras.apt.core.Logger;
import guru.bug.austras.apt.model.ComponentModel;
import guru.bug.austras.apt.model.ModuleModel;
import guru.bug.austras.apt.model.ModuleModelSerializer;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Stream;

public class ComponentMap {
    private final Map<ComponentKey, HashSet<ComponentModel>> index = new HashMap<>();
    private final ModuleModel model = new ModuleModel();
    private final Logger log;

    public ComponentMap(Logger log) {
        this.log = log;
    }

    public void addComponent(ComponentModel componentModel) {
        model.components().add(componentModel);
        var qualifiers = componentModel.getQualifiers();
        for (var a : componentModel.getTypes()) {
            var key = new ComponentKey(a, qualifiers);
            put(key, componentModel);
        }
    }

    public Stream<ComponentModel> allComponentsStream() {
        return model.components().stream();
    }

    public ComponentModel findSingleComponentModel(ComponentKey key) {
        var comps = index.get(key);
        if (comps == null) {
            return null;
        }
        if (comps.size() > 1) {
            throw new IllegalArgumentException("Too many components " + key);
        }
        return comps.stream()
                .findFirst()
                .orElse(null);
    }

    private void put(ComponentKey key, ComponentModel value) {
        index.computeIfAbsent(key, k -> new HashSet<>()).add(value);
    }


    @Override
    public String toString() {
        var result = new StringWriter(512);
        try (var out = new PrintWriter(result)) {
            serialize(out);
        } catch (IOException e) {
            throw new RuntimeException(e); // TODO better error handling
        }
        return result.toString();
    }

    public void serialize(Writer out) throws IOException {
        ModuleModelSerializer.store(model, out);
    }
}
