package guru.bug.austras.apt.core;

import guru.bug.austras.apt.core.componentmap.UniqueNameGenerator;
import guru.bug.austras.apt.model.ComponentModel;
import guru.bug.austras.annotations.Qualifier;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.PUBLIC;

public class ModelUtils {
    private final Logger log;
    private final UniqueNameGenerator uniqueNameGenerator;
    private final Types typeUtils;
    private final Elements elementUtils;
    private final DeclaredType providerInterfaceType;

    public ModelUtils(Logger log, UniqueNameGenerator uniqueNameGenerator, Types typeUtils, Elements elementUtils, DeclaredType providerInterfaceType) {
        this.log = log;
        this.uniqueNameGenerator = uniqueNameGenerator;
        this.typeUtils = typeUtils;
        this.elementUtils = elementUtils;
        this.providerInterfaceType = providerInterfaceType;
    }

    public ComponentModel createComponentModel(TypeElement type) {
        Set<Modifier> modifiers = type.getModifiers();
        if (type.getKind() != ElementKind.CLASS || modifiers.contains(ABSTRACT) || !modifiers.contains(PUBLIC)) {
            throw new IllegalArgumentException("type must be a public, not abstract class" );
        }
        var ancestors = collectAllAncestor(type).stream()
                .map(TypeMirror::toString)
                .collect(Collectors.toList());
        ancestors.add(type.toString());
        log.debug("All superclasses and interfaces: %s", ancestors);
        var varName = uniqueNameGenerator.findFreeVarName(type);
        var qualifier = extractQualifier(type);
        var model = new ComponentModel();
        model.setQualifier(qualifier);
        model.setInstantiable(type.toString());
        model.setName(varName);
        model.setTypes(ancestors);

        return model;
    }

    public String extractQualifier(Element type) {
        var qualifier = type.getAnnotation(Qualifier.class);
        return qualifier == null ? null : qualifier.value();
    }

    public String extractQualifier(TypeMirror type) {
        var qualifier = type.getAnnotation(Qualifier.class);
        return qualifier == null ? null : qualifier.value();
    }

    private Set<DeclaredType> collectAllAncestor(TypeElement componentElement) {
        var componentType = componentElement.asType();
        var result = new HashSet<DeclaredType>();
        var checked = new HashSet<TypeMirror>();
        var toCheck = new LinkedList<TypeMirror>(typeUtils.directSupertypes(componentType));
        while (!toCheck.isEmpty()) {
            var cur = toCheck.remove();
            log.debug("Checking %s", cur);
            if (checked.contains(cur)) {
                log.debug("Already checked" );
                continue;
            }
            checked.add(cur);
            if (cur.getKind() != TypeKind.DECLARED) {
                log.debug("%s is not a declared type", cur);
                continue;
            }
            var curType = ((DeclaredType) cur);
            var curElem = curType.asElement();
            if (curElem.getKind() != ElementKind.CLASS && curElem.getKind() != ElementKind.INTERFACE) {
                log.debug("%s is not an interface or class", curElem);
                continue;
            }
            var curDeclElem = (TypeElement) curElem;
            result.add(curType);
            var supertypes = typeUtils.directSupertypes(curType);
            log.debug("adding supertypes to check-queue: %s", supertypes);
            toCheck.addAll(supertypes);
        }
        return result;
    }

    public DeclaredType extractComponentTypeFromProvider(TypeElement providerElement) {
        Element providerInterfaceElement = providerInterfaceType.asElement();
        var tmp = collectAllAncestor(providerElement).stream()
                .filter(dt -> dt.asElement().equals(providerInterfaceElement))
                .map(dt -> dt.getTypeArguments().get(0))
                .distinct()
                .collect(Collectors.toList());
        if (tmp.size() != 1) {
            throw new IllegalArgumentException("Not expected count of type parameters: " + tmp);
        }
        var componentType = (DeclaredType) tmp.get(0);
        log.debug("Provider %s provides component: %s", providerElement, componentType);
        return componentType;
    }

}
