package guru.bug.austras.apt.core.componentmap;

import guru.bug.austras.apt.model.ModuleModel;
import guru.bug.austras.apt.model.ComponentModel;
import guru.bug.austras.apt.core.Logger;
import guru.bug.austras.apt.model.ModuleModelSerializer;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
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

//    public void resolveProvider(TypeElement type) {
//        log.debug("Adding as component provider" );
//        var params = collectProviderParams(type);
//        var desc = new ProviderDescription(varName, type, params);
//        providers.add(desc);
//    }

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
            throw new IllegalArgumentException("Component " + key + " not found" );
        }
        if (comps.size() > 1) {
            throw new IllegalArgumentException("Too many components " + key);
        }
        return comps.stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Component " + key + " not found" ));
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

//    private ComponentModel convertToModel(ComponentDescription cd) {
//        var cm = new ComponentModel();
//        cm.setName(cd.getVarName());
//        cm.setInstantiable(cd.getType().toString());
//        cm.setQualifier(cd.get().toString());
//        var pm = convertToModel(cd.getProvider());
//        cm.setProvider(pm);
//        return cm;
//    }
//
//    private ProviderModel convertToModel(ProviderDescription pd) {
//        if (pd == null) {
//            return null;
//        }
//        var pm = new ProviderModel();
//        pm.setName(pd.getVarName());
//        pm.setInstantiable(pd.getType().toString());
//        var deps = pd.getParams().stream()
//                .map(ppd -> {
//                    var dm = new DependencyModel();
//                    dm.setType(ppd.getKey().getType());
//                    dm.setQualifier(ppd.getKey().getQualifier());
//                    dm.setName(ppd.getVarName());
//                    return dm;
//                })
//                .collect(Collectors.toList());
//        pm.dependencies().addAll(deps);
//        return pm;
//    }
}
