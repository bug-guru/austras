package guru.bug.fuzzbagel.apt.core.componentmap;

import guru.bug.fuzzbagel.apt.core.Logger;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.*;

public class ComponentMap {
    private final Map<DeclaredType, HashSet<ComponentDescription>> components = new HashMap<>();
    private final Logger log;

    public ComponentMap(Logger log) {
        this.log = log;
    }

    public void addClass(TypeElement type) {
        var ifaces = collectAllAncestor(type);
        log.debug("All superclasses and interfaces: %s", ifaces);
        var desc = new ComponentDescription(type);
        ifaces.forEach(e -> put(e, desc));
        put((DeclaredType) type.asType(), desc);
    }

    private void put(DeclaredType key, ComponentDescription value) {
        components.computeIfAbsent(key, k -> new HashSet<>()).add(value);
    }

    private List<DeclaredType> collectAllAncestor(TypeElement type) {
        var result = new ArrayList<DeclaredType>();
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
                .sorted(Comparator.comparing(it -> it.getKey().toString()))
                .forEach(
                        ctrl -> {
                            result.append("\t" ).append(ctrl.getKey()).append(":\n" );
                            ctrl.getValue().stream()
                                    .sorted(Comparator.comparing(Object::toString))
                                    .forEach(desc -> result.append("\t\t" ).append(desc).append("\n" ));
                        });
        return result.toString();
    }
}
