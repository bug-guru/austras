package guru.bug.austras.apt.core.componentmap;

import guru.bug.austras.apt.model.ComponentModel;
import guru.bug.austras.apt.model.ModuleModel;
import guru.bug.austras.apt.model.ModuleModelSerializer;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.lang.String.format;

public class ComponentMap {
    private static final Logger log = Logger.getLogger(ComponentMap.class.getName());
    private final Map<ComponentKey, HashSet<ComponentModel>> index = new HashMap<>();
    private final ModuleModel model = new ModuleModel();

    public void addComponents(Collection<ComponentModel> componentModels) {
        componentModels.forEach(this::addComponent);
    }

    public void addComponent(ComponentModel componentModel) {
        log.fine(() -> format("Indexing component %s", componentModel.getInstantiable()));
        model.components().add(componentModel);
        var qualifiers = componentModel.getQualifiers();
        for (var a : componentModel.getTypes()) {
            var key = new ComponentKey(a, qualifiers);
            log.finer(() -> format("--- key %s", key));
            put(key, componentModel);
        }
    }

    public Stream<ComponentModel> allComponentsStream() {
        return model.components().stream();
    }

    public Set<ComponentKey> getKeys() {
        return Collections.unmodifiableSet(index.keySet());
    }

    public boolean hasComponent(ComponentKey key) {
        var comps = index.get(key);
        return comps != null && !comps.isEmpty();
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

    public Collection<ComponentModel> findComponentModels(ComponentKey key) {
        var comps = index.get(key);
        if (comps == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableCollection(comps);
    }

    public Collection<ComponentModel> findAndRemoveComponentModels(ComponentKey key) {
        var comps = index.remove(key);
        if (comps == null) {
            return Collections.emptyList();
        }
        for (var c : comps) {
            for (var t : c.getTypes()) {
                var k = new ComponentKey(t, c.getQualifiers());
                var m = index.get(k);
                if (m != null) {
                    m.remove(c);
                    if (m.isEmpty()) {
                        index.remove(k);
                    }
                }
            }
        }
        return Collections.unmodifiableCollection(comps);
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
