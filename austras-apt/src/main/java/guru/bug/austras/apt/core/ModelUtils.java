package guru.bug.austras.apt.core;

import guru.bug.austras.annotations.Cached;
import guru.bug.austras.annotations.NoCached;
import guru.bug.austras.annotations.Qualifier;
import guru.bug.austras.apt.core.componentmap.UniqueNameGenerator;
import guru.bug.austras.apt.model.CachingKind;
import guru.bug.austras.apt.model.ComponentModel;
import guru.bug.austras.apt.model.DependencyModel;

import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.*;
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

    public ComponentModel createComponentModel(DeclaredType type) {
        var element = (TypeElement) type.asElement();
        Set<Modifier> modifiers = element.getModifiers();
        if (element.getKind() != ElementKind.CLASS || modifiers.contains(ABSTRACT) || !modifiers.contains(PUBLIC)) {
            throw new IllegalArgumentException("type must be a public, not abstract class" );
        }
        var model = createComponentModel(type, element);

        var dependencies = collectConstructorParams(type);
        model.setDependencies(dependencies);

        return model;
    }

    public ComponentModel createComponentModel(DeclaredType type, TypeElement metaInfo) {
        var ancestors = collectAllAncestor(type).stream()
                .map(TypeMirror::toString)
                .collect(Collectors.toList());
        ancestors.add(type.toString());
        log.debug("All superclasses and interfaces: %s", ancestors);
        var varName = uniqueNameGenerator.findFreeVarName(type);
        var qualifiers = extractQualifiers(metaInfo);
        var model = new ComponentModel();

        model.setQualifiers(qualifiers);
        model.setInstantiable(type.toString());
        model.setName(varName);
        model.setTypes(ancestors);

        setupCaching(model, metaInfo);

        return model;
    }

    private void setupCaching(ComponentModel model, TypeElement type) {
        var cachedAnnotation = type.getAnnotation(Cached.class);
        var noCachedAnnotation = type.getAnnotation(NoCached.class);
        if (cachedAnnotation == null && noCachedAnnotation == null) {
            model.setCachingKind(CachingKind.EAGER_SINGLETON);
            model.setCacheType(null);
        } else if (noCachedAnnotation != null) {
            model.setCachingKind(CachingKind.NO_CACHE);
            model.setCacheType(null);
        } else {
            model.setCachingKind(CachingKind.CACHED);
            model.setCacheType(cachedAnnotation.value().getName());
        }
    }

    public List<String> extractQualifiers(Element element) {
        Set<String> qualifiers = element.getAnnotationMirrors().stream()
                .filter(am -> {
                    Element annotationElement = typeUtils.asElement(am.getAnnotationType());
                    return annotationElement.getAnnotation(Qualifier.class) != null;
                })
                .map(Object::toString)
                .collect(Collectors.toCollection(HashSet::new));
        var rawQualifier = element.getAnnotation(Qualifier.class);
        if (rawQualifier != null && rawQualifier.value().length > 0) {
            qualifiers.addAll(List.of(rawQualifier.value()));
        }
        return new ArrayList<>(qualifiers);
    }

    private Set<DeclaredType> collectAllAncestor(TypeElement componentElement) {
        return collectAllAncestor((DeclaredType) componentElement.asType());
    }

    private Set<DeclaredType> collectAllAncestor(DeclaredType componentType) {
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

    public List<DependencyModel> collectConstructorParams(TypeElement type) {
        return collectConstructorParams((DeclaredType) type.asType());
    }

    public List<DependencyModel> collectConstructorParams(DeclaredType type) {
        ExecutableElement constructor = (ExecutableElement) type.asElement().getEnclosedElements().stream()
                .filter(m -> m.getKind() == ElementKind.CONSTRUCTOR)
                .filter(m -> m.getModifiers().contains(PUBLIC))
                .findFirst()
                .orElse(null);
        if (constructor == null) {
            return List.of();
        }

        return constructor.getParameters().stream()
                .map(this::createDependencyModel)
                .collect(Collectors.toList());
    }

    private DependencyModel createDependencyModel(VariableElement paramElement) {
        var qualifiers = extractQualifiers(paramElement);
        String varName = paramElement.getSimpleName().toString();
        var result = new DependencyModel();
        result.setName(varName);
        result.setQualifiers(qualifiers);
        result.setType(paramElement.asType().toString());
        return result;
    }

}
