package guru.bug.fuzzbagel.apt.core.componentmap;

import guru.bug.fuzzbagel.apt.core.Logger;
import guru.bug.fuzzbagel.apt.model.*;

import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ComponentMap {
    private final UniqueNameGenerator uniqueNameGenerator = new UniqueNameGenerator();
    private final Map<ComponentKey, HashSet<BaseDescription>> components = new HashMap<>();
    private final Queue<ProviderDescription> providers = new LinkedList<>();
    private final Logger log;
    private final TypeElement providerType;
    private boolean hasProviderAncestor;

    public ComponentMap(Logger log, TypeElement providerType) {
        this.log = log;
        this.providerType = providerType;
    }

    public void addClass(TypeElement type) {
        if (type.getKind() != ElementKind.CLASS || type.getModifiers().contains(Modifier.ABSTRACT)) {
            throw new IllegalArgumentException("type must be a not abstract class");
        }
        var ancestors = collectAllAncestor(type);
        log.debug("All superclasses and interfaces: %s", ancestors);
        var varName = uniqueNameGenerator.findFreeVarName(type);
        BaseDescription desc;
        if (hasProviderAncestor) {
            log.debug("Adding as component provider");
            var params = collectProviderParams(type);
            desc = new ProviderDescription(varName, type, params);
            providers.add((ProviderDescription) desc);
        } else {
            desc = new ComponentDescription(varName, type);
        }
        log.debug("Adding as component (var: %s)", varName);
        ancestors.stream()
                .map(ComponentKey::new)
                .forEach(k -> put(k, desc));
        var declaredType = (DeclaredType) type.asType();
        var key = new ComponentKey(declaredType);
        put(key, desc);
    }

    private List<ProviderParamDescription> collectProviderParams(TypeElement type) {
        ExecutableElement constructor = (ExecutableElement) type.getEnclosedElements().stream()
                .filter(m -> m.getKind() == ElementKind.CONSTRUCTOR)
                .filter(m -> m.getModifiers().contains(Modifier.PUBLIC))
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
        ComponentKey key = new ComponentKey(paramDeclType);
        return new ProviderParamDescription(key, varName);
    }


    public void resolveProviders() {
        log.debug("Resolving providers");
        ProviderDescription provider;
        while ((provider = providers.poll()) != null) {
            log.debug("Resolving provider %s", provider);
            var type = extractComponentTypeFromProvider(provider.getType());
            var key = new ComponentKey(type);
            var foundSet = components.get(key);
            if (foundSet == null || foundSet.size() != 1) {
                throw new IllegalStateException("expected only one component"); // TODO better error handling
            }
            var cd = (ComponentDescription) foundSet.stream().findFirst().orElseThrow();
            log.debug("Setting provider %s for %s", provider.getType(), cd.getType());
            cd.setProvider(provider);
        }
    }

    private DeclaredType extractComponentTypeFromProvider(TypeElement provider) {
        var providerAncestors = provider.getInterfaces().stream()
                .filter(tm -> ((DeclaredType) tm).asElement().equals(providerType))
                .filter(tm -> ((DeclaredType) tm).getTypeArguments().size() == 1)
                .filter(tm -> ((DeclaredType) tm).getTypeArguments().get(0).getKind() == TypeKind.DECLARED)
                .collect(Collectors.toList());
        if (providerAncestors.size() != 1) {
            throw new IllegalArgumentException("Unexpected count of ComponentProvider implemented interfaces");
        }
        DeclaredType providerAncestor = (DeclaredType) providerAncestors.get(0);
        log.debug("ancestor: %s", providerAncestor);
        var typeArguments = providerAncestor.getTypeArguments();
        if (typeArguments.size() != 1) {
            throw new IllegalArgumentException("Unexpected count of type arguments");
        }
        return (DeclaredType) typeArguments.get(0);
    }

    public Stream<BaseDescription> allComponentsStream() {
        return components.values().stream().flatMap(Collection::stream).distinct();
    }

    public BaseDescription findSingleDeclaration(DeclaredType type) {
        var comps = components.get(new ComponentKey(type));
        if (comps == null) {
            throw new IllegalArgumentException("Component " + type + " not found");
        }
        if (comps.size() > 1) {
            throw new IllegalArgumentException("Too many components " + type);
        }
        return comps.stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Component " + type + " not found"));
    }

    private void put(ComponentKey key, BaseDescription value) {
        components.computeIfAbsent(key, k -> new HashSet<>()).add(value);
    }

    private Set<DeclaredType> collectAllAncestor(TypeElement type) {
        hasProviderAncestor = false;
        var result = new HashSet<DeclaredType>();
        var checked = new HashSet<TypeMirror>();
        var toCheck = new LinkedList<TypeMirror>(type.getInterfaces());
        toCheck.add(type.getSuperclass());
        while (!toCheck.isEmpty()) {
            var cur = toCheck.remove();
            log.debug("Checking %s", cur);
            if (checked.contains(cur)) {
                log.debug("Already checked");
                continue;
            }
            checked.add(cur);
            if (cur.getKind() != TypeKind.DECLARED) {
                log.debug("%s is not declared type", cur);
                continue;
            }
            var curType = ((DeclaredType) cur);
            var curElem = curType.asElement();
            if (curElem.getKind() != ElementKind.CLASS && curElem.getKind() != ElementKind.INTERFACE) {
                log.debug("%s is not interface or class", curElem);
                continue;
            }
            var curDeclElem = (TypeElement) curElem;
            if (curDeclElem.equals(providerType)) {
                log.debug("marking as provider");
                hasProviderAncestor = true;
            }
            result.add(curType);
            var sup = curDeclElem.getSuperclass();
            if (sup.getKind() == TypeKind.NONE) {
                log.debug("no superclass found");
            } else {
                log.debug("adding superclass %s to check-queue", sup);
                toCheck.add(sup);
            }
            var toAdd = curDeclElem.getInterfaces();
            if (toAdd.isEmpty()) {
                log.debug("no implemented interfaces");
            } else {
                log.debug("adding %s to the check-queue", toAdd);
                toCheck.addAll(toAdd);
            }
        }
        return result;
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
        var tmpMap = new HashMap<String, ComponentModel>();
        components.values().stream()
                .flatMap(Collection::stream)
                .filter(b -> b instanceof ComponentDescription)
                .distinct()
                .map(b -> (ComponentDescription) b)
                .forEach(cd -> {
                    var cm = convertToModel(cd);
                    tmpMap.put(cm.getName(), cm);
                });

        components.forEach((key, compSet) -> {
            compSet.stream()
                    .filter(b -> b instanceof ComponentDescription)
                    .map(b -> (ComponentDescription) b)
                    .forEach(cd -> {
                        var cm = tmpMap.get(cd.getVarName());
                        var km = convertToModel(key);
                        cm.keys().add(km);
                    });
        });

        var model = new BagelModel();
        model.components().addAll(tmpMap.values());
        BagelModelSerializer.store(model, out);
    }

    private ComponentKeyModel convertToModel(ComponentKey ck) {
        var km = new ComponentKeyModel();
        km.setType(ck.getType().toString());
        return km;
    }

    private ComponentModel convertToModel(ComponentDescription cd) {
        var cm = new ComponentModel();
        cm.setName(cd.getVarName());
        cm.setInstantiable(cd.getType().toString());
        var pm = convertToModel(cd.getProvider());
        cm.setProvider(pm);
        return cm;
    }

    private ProviderModel convertToModel(ProviderDescription pd) {
        if (pd == null) {
            return null;
        }
        var pm = new ProviderModel();
        pm.setName(pd.getVarName());
        pm.setInstantiable(pd.getType().toString());
        var deps = pd.getParams().stream()
                .map(ppd -> {
                    var dm = new DependencyModel();
                    var ckm = new ComponentKeyModel();
                    ckm.setType(ppd.);
                })
                .collect(Collectors.toList());
        pm.dependencies().addAll()
        return pm;
    }
}
