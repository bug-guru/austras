package guru.bug.fuzzbagel.apt.core;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.*;

public class ComponentMap {
  private final Map<TypeElement, Set<TypeElement>> components =
      new TreeMap<>(Comparator.comparing(a -> a.getQualifiedName().toString()));
  private final Logger log;

  public ComponentMap(Logger log) {
    this.log = log;
  }

  public void addClass(TypeElement type) {
    List<TypeElement> ifaces = collectAllAncestor(type);
    log.debug("All superclasses and interfaces: %s", ifaces);
    ifaces.forEach(e -> put(e, type));
    put(type, type);
  }

  private void put(TypeElement key, TypeElement value) {
    var lst = components.computeIfAbsent(key, k -> new HashSet<>());
    lst.add(value);
  }

  private List<TypeElement> collectAllAncestor(TypeElement type) {
    var result = new ArrayList<TypeElement>();
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
      var curElem = ((DeclaredType) cur).asElement();
      if (curElem.getKind() != ElementKind.CLASS && curElem.getKind() != ElementKind.INTERFACE) {
        log.debug("%s is not interface or class", curElem);
        continue;
      }
      var curIfaceElem = (TypeElement) curElem;
      result.add(curIfaceElem);
      if (curElem.getKind() == ElementKind.CLASS) {
        log.debug("%s is class", curElem);
        var sup = curIfaceElem.getSuperclass();
        if (sup.getKind() == TypeKind.NONE) {
          log.debug("no superclass found");
        } else {
          log.debug("adding superclass %s to check-queue", sup);
          toCheck.add(sup);
        }
      } else if (curElem.getKind() == ElementKind.INTERFACE) {
        log.debug("%s is interface", curElem);
      }
      var toAdd = curIfaceElem.getInterfaces();
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
    StringBuilder result = new StringBuilder(500);
    result.append("Component map:\n");
    components.forEach(
        (k, v) -> {
          result.append("\t").append(k).append(":\n");
          v.forEach(i -> result.append("\t\t").append(i).append("\n"));
        });
    return result.toString();
  }
}
