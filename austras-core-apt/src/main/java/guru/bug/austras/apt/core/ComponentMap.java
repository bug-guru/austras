package guru.bug.austras.apt.core;

import guru.bug.austras.apt.core.model.ComponentKey;
import guru.bug.austras.apt.core.model.ComponentModel;
import guru.bug.austras.apt.core.model.ModuleModel;
import guru.bug.austras.apt.core.process.ModuleModelSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
import java.util.stream.Stream;

public class ComponentMap {
    private static final Logger log = LoggerFactory.getLogger(ComponentMap.class);
    private final Index stageIndex = new Index();
    private final Index index = new Index();
    private final ModuleModel model = new ModuleModel();
    private final List<ComponentModel> imported = new ArrayList<>();

    public void publishComponents(Collection<ComponentModel> componentModels) {
        componentModels.forEach(this::publishComponent);
    }

    public void stageComponent(ComponentModel componentModel) {
        log.debug("Staging component {}", componentModel.getInstantiable());
        stageIndex.add(componentModel);
    }

    public void publishComponent(ComponentModel componentModel) {
        log.debug("Publishing component {}", componentModel.getInstantiable());
        if (componentModel.isImported()) {
            imported.add(componentModel);
        } else {
            model.components().add(componentModel);
        }
        index.add(componentModel);
    }

    public Stream<ComponentModel> allComponentsStream() {
        return Stream.concat(imported.stream(), model.components().stream());
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


    private static class Index {
        final Map<ComponentKey, HashSet<ComponentModel>> components = new HashMap<>();

        Set<ComponentModel> find(ComponentKey key) {
            var result = components.get(key);
            if (result == null) {
                return Set.of();
            } else {
                return Set.copyOf(result);
            }
        }

        void add(ComponentModel componentModel) {
            var qualifiers = componentModel.getQualifiers();
            for (var a : componentModel.getTypes()) {
                var key = new ComponentKey(a, qualifiers);
                var componentModels = components.computeIfAbsent(key, k -> new HashSet<>());
                componentModels.add(componentModel);
            }
        }

        Collection<ComponentModel> remove(ComponentKey key) {
            var comps = components.remove(key);
            if (comps == null) {
                return Collections.emptyList();
            }
            for (var c : comps) {
                for (var t : c.getTypes()) {
                    var k = new ComponentKey(t, c.getQualifiers());
                    var m = components.get(k);
                    if (m != null) {
                        m.remove(c);
                        if (m.isEmpty()) {
                            components.remove(k);
                        }
                    }
                }
            }
            return Collections.unmodifiableCollection(comps);
        }

    }
}
