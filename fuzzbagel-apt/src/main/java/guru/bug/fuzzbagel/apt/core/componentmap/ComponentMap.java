package guru.bug.fuzzbagel.apt.core.componentmap;

import guru.bug.fuzzbagel.apt.core.Logger;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ComponentMap {
    private final UniqueNameGenerator uniqueNameGenerator = new UniqueNameGenerator();
    private final Map<String, HashSet<ComponentDescription>> components = new HashMap<>();
    private final Queue<TypeElement> providers = new LinkedList<>();
    private final Logger log;
    private final TypeElement providerType;
    private boolean hasProviderAncestor;

    public ComponentMap(Logger log, TypeElement providerType) {
        this.log = log;
        this.providerType = providerType;
    }

    public void addClass(TypeElement type) {
        if (type.getKind() != ElementKind.CLASS || type.getModifiers().contains(Modifier.ABSTRACT)) {
            throw new IllegalArgumentException("type must be a not abstract class" );
        }
        var ancestors = collectAllAncestor(type);
        log.debug("All superclasses and interfaces: %s", ancestors);

        var varName = uniqueNameGenerator.findFreeVarName(type);
        var providerVarName = uniqueNameGenerator.findFreeVarName(varName + "Provider" );
        var desc = new ComponentDescription(varName, type, providerVarName);
        log.debug("Adding as component (var: %s; provider var: %s)", varName, providerVarName);
        ancestors.forEach(e -> put(e.toString(), desc));
        DeclaredType declaredType = (DeclaredType) type.asType();
        put(declaredType.toString(), desc);

        if (hasProviderAncestor) {
            log.debug("Adding as component provider" );
            providers.add(type);
            desc.setProvider(true);
        }

    }


    public void resolveProviders() {
        log.debug("Resolving providers" );
        TypeElement provider;
        while ((provider = providers.poll()) != null) {
            log.debug("Resolving provider %s", provider);
            var type = extractComponentTypeFromProvider(provider);
            final var p = provider; // final required for accessing from lambdas
            components.get(type.toString()).stream().findFirst().ifPresent(cd -> {
                log.debug("Setting provider %s for %s", p, cd.getComponentType());
                cd.setProviderType(p);
            });
        }
    }

    private DeclaredType extractComponentTypeFromProvider(TypeElement provider) {
        var providerAncestors = provider.getInterfaces().stream()
                .filter(tm -> ((DeclaredType) tm).asElement().equals(providerType))
                .filter(tm -> ((DeclaredType) tm).getTypeArguments().size() == 1)
                .filter(tm -> ((DeclaredType) tm).getTypeArguments().get(0).getKind() == TypeKind.DECLARED)
                .collect(Collectors.toList());
        if (providerAncestors.size() != 1) {
            throw new IllegalArgumentException("Unexpected count of ComponentProvider implemented interfaces" );
        }
        DeclaredType providerAncestor = (DeclaredType) providerAncestors.get(0);
        log.debug("ancestor: %s", providerAncestor);
        var typeArguments = providerAncestor.getTypeArguments();
        if (typeArguments.size() != 1) {
            throw new IllegalArgumentException("Unexpected count of type arguments" );
        }
        return (DeclaredType) typeArguments.get(0);
    }

    public Stream<ComponentDescription> allComponentsStream() {
        return components.values().stream().flatMap(Collection::stream).distinct();
    }

    public ComponentDescription findSingleDeclaration(DeclaredType type) {
        var comps = components.get(type.toString());
        if (comps == null) {
            throw new IllegalArgumentException("Component " + type + " not found" );
        }
        if (comps.size() > 1) {
            throw new IllegalArgumentException("Too many components " + type);
        }
        return comps.stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Component " + type + " not found" ));
    }

    private void put(String key, ComponentDescription value) {
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
                log.debug("Already checked" );
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
                log.debug("marking as provider" );
                hasProviderAncestor = true;
            }
            result.add(curType);
            var sup = curDeclElem.getSuperclass();
            if (sup.getKind() == TypeKind.NONE) {
                log.debug("no superclass found" );
            } else {
                log.debug("adding superclass %s to check-queue", sup);
                toCheck.add(sup);
            }
            var toAdd = curDeclElem.getInterfaces();
            if (toAdd.isEmpty()) {
                log.debug("no implemented interfaces" );
            } else {
                log.debug("adding %s to the check-queue", toAdd);
                toCheck.addAll(toAdd);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(500);
        result.append("componentMap:\n" );
        components.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach(
                        ctrl -> {
                            result.append("\t" ).append(ctrl.getKey()).append(":\n" );
                            ctrl.getValue().stream()
                                    .sorted(Comparator.comparing(Object::toString))
                                    .forEach(desc -> result.append("\t\t" ).append(desc).append("\n" ));
                        });
        return result.toString();
    }

    public void serialize(PrintWriter out) {
        var exported = new HashSet<String>();
        out.write("components:\n" );
        components.values().stream()
                .flatMap(Collection::stream)
                .distinct()
                .forEach(cd -> {
                    out.printf("- component:\n" +
                                    "\tname: %s\n" +
                                    "\ttype: %s\n" +
                                    "\tprovider: %s\n",
                            cd.getVarName(),
                            cd.getComponentType(),
                            cd.getProviderType());
                });
    }
}
