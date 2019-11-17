package guru.bug.austras.apt.core.componentmap;

import guru.bug.austras.apt.model.ComponentModel;
import guru.bug.austras.apt.model.ModuleModel;
import guru.bug.austras.apt.model.ModuleModelSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
import java.util.stream.Stream;

public class ComponentMap {
    private static final Logger log = LoggerFactory.getLogger(ComponentMap.class);
    private final Map<ComponentKey, HashSet<ComponentModel>> index = new HashMap<>();
    private final ModuleModel model = new ModuleModel();
    private final List<ComponentModel> imported = new ArrayList<>();

    public void addComponents(Collection<ComponentModel> componentModels) {
        componentModels.forEach(this::addComponent);
    }

    public void addComponent(ComponentModel componentModel) {
        log.debug("Indexing component {}", componentModel.getInstantiable());
        if (componentModel.isImported()) {
            imported.add(componentModel);
        } else {
            model.components().add(componentModel);
        }
        var qualifiers = componentModel.getQualifiers();
        for (var a : componentModel.getTypes()) {
            var key = new ComponentKey(a, qualifiers);
            log.debug("--- key {}", key);
            put(key, componentModel);
        }
    }

    public Stream<ComponentModel> allComponentsStream() {
        return Stream.concat(imported.stream(), model.components().stream());
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
            throw new IllegalArgumentException("Too many components " + key + "; components: " + comps);
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
        var componentModels = index.computeIfAbsent(key, k -> new HashSet<>());
        componentModels.add(value);
    }

    @Override
    public String toString() {
        var result = new StringWriter(512);
        try (var out = new PrintWriter(result)) {
            serialize(out);
        }
        return result.toString();
    }

    public void serialize(Writer out) {
        ModuleModelSerializer.store(model, out);
    }

}
