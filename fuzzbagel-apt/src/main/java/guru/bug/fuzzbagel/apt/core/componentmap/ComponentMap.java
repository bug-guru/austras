package guru.bug.fuzzbagel.apt.core.componentmap;

import guru.bug.fuzzbagel.annotations.Qualifier;
import guru.bug.fuzzbagel.apt.core.Logger;
import guru.bug.fuzzbagel.apt.model.BagelModel;
import guru.bug.fuzzbagel.apt.model.ComponentModel;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static javax.lang.model.element.Modifier.PUBLIC;

public class ComponentMap {
    private final Map<ComponentKey, HashSet<ComponentModel>> index = new HashMap<>();
    private final BagelModel model = new BagelModel();
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
        var qualifier = componentModel.getQualifier();
        for (var a : componentModel.getTypes()) {
            var key = new ComponentKey(a, qualifier);
            put(key, componentModel);
        }
    }

    private List<ProviderParamDescription> collectProviderParams(TypeElement type) {
        ExecutableElement constructor = (ExecutableElement) type.getEnclosedElements().stream()
                .filter(m -> m.getKind() == ElementKind.CONSTRUCTOR)
                .filter(m -> m.getModifiers().contains(PUBLIC))
                .findFirst()
                .orElse(null);
        if (constructor == null) {
            return List.of();
        }

        return constructor.getParameters().stream()
                .map(this::createParamDesc)
                .collect(Collectors.toList());
    }

    private ProviderParamDescription createParamDesc(VariableElement param) {
        DeclaredType paramDeclType = (DeclaredType) param.asType();
        String varName = param.getSimpleName().toString();
        Qualifier qualifier = param.getAnnotation(Qualifier.class);
        ComponentKey key = new ComponentKey(paramDeclType, qualifier);
        return new ProviderParamDescription(key, varName);
    }


//    public Stream<ComponentDescription> allComponentsStream() {
//        return index.values().stream().flatMap(Collection::stream).distinct();
//    }

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
//        var tmpMap = new HashMap<String, ComponentModel>();
//        index.values().stream()
//                .flatMap(Collection::stream)
//                .filter(b -> b instanceof ComponentDescription)
//                .distinct()
//                .map(b -> b)
//                .forEach(cd -> {
//                    var cm = convertToModel(cd);
//                    tmpMap.put(cm.getName(), cm);
//                });
//
//        index.forEach((key, compSet) -> {
//            compSet.stream()
//                    .filter(b -> b instanceof ComponentDescription)
//                    .map(b -> b)
//                    .forEach(cd -> {
//                        var cm = tmpMap.get(cd.getVarName());
//                        cm.types().add(cd.getType().toString());
//                    });
//        });
//
//        var model = new BagelModel();
//        model.components().addAll(tmpMap.values());
//        BagelModelSerializer.store(model, out);
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
