package guru.bug.austras.apt.core;

import guru.bug.austras.annotations.Cached;
import guru.bug.austras.annotations.NoCached;
import guru.bug.austras.annotations.Qualifier;
import guru.bug.austras.annotations.QualifierProperty;
import guru.bug.austras.apt.core.componentmap.UniqueNameGenerator;
import guru.bug.austras.apt.core.generators.CachingProviderGenerator;
import guru.bug.austras.apt.core.generators.EagerSingletonProviderGenerator;
import guru.bug.austras.apt.core.generators.NonCachingProviderGenerator;
import guru.bug.austras.apt.core.generators.ProviderGenerator;
import guru.bug.austras.apt.model.ComponentModel;
import guru.bug.austras.apt.model.DependencyModel;
import guru.bug.austras.apt.model.QualifierModel;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleAnnotationValueVisitor9;
import javax.lang.model.util.Types;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.PUBLIC;

public class ModelUtils {
    private final static AnnotationValueVisitor<String, Void> annotationToStringVisitor = new SimpleAnnotationValueVisitor9<>() {
        @Override
        protected String defaultAction(Object o, Void aVoid) {
            return Objects.toString(o);
        }
    };
    private final Logger log;
    private final UniqueNameGenerator uniqueNameGenerator;
    private final Types typeUtils;
    private final Elements elementUtils;
    private final DeclaredType providerInterfaceType;
    private final ProcessingEnvironment processingEnv;
    private final String componentCacheVarName;

    public ModelUtils(Logger log, UniqueNameGenerator uniqueNameGenerator, ProcessingEnvironment processingEnv, DeclaredType providerInterfaceType) {
        this.log = log;
        this.uniqueNameGenerator = uniqueNameGenerator;
        this.processingEnv = processingEnv;
        this.typeUtils = processingEnv.getTypeUtils();
        this.elementUtils = processingEnv.getElementUtils();
        this.providerInterfaceType = providerInterfaceType;
        this.componentCacheVarName = uniqueNameGenerator.findFreeVarName("componentCache");
    }

    public ComponentModel createComponentModel(DeclaredType type) {
        var element = (TypeElement) type.asElement();
        Set<Modifier> modifiers = element.getModifiers();
        if (element.getKind() != ElementKind.CLASS || modifiers.contains(ABSTRACT) || !modifiers.contains(PUBLIC)) {
            throw new IllegalArgumentException("type must be a public, not abstract class" );
        }

        return createComponentModel(type, element);
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

        return model;
    }

    public ProviderGenerator createProviderGeneratorFor(ComponentModel componentModel) {
        TypeElement componentElement = elementUtils.getTypeElement(componentModel.getInstantiable());
        var cachedAnnotation = componentElement.getAnnotation(Cached.class);
        var cacheType = extractValueFromCachedAnnotation(cachedAnnotation);
        var noCachedAnnotation = componentElement.getAnnotation(NoCached.class);
        List<DependencyModel> dependencies = collectConstructorParams(componentElement);
        if (cachedAnnotation == null && noCachedAnnotation == null) {
            return new EagerSingletonProviderGenerator(processingEnv, componentModel, dependencies);
        } else if (noCachedAnnotation != null) {
            return new NonCachingProviderGenerator(processingEnv, componentModel, dependencies);
        } else {
            return new CachingProviderGenerator(processingEnv, componentModel, dependencies, cacheType, componentCacheVarName);
        }
    }

    private String extractValueFromCachedAnnotation(Cached cachedAnnotation) {
        if (cachedAnnotation != null) {
            try {
                cachedAnnotation.value();
            } catch (MirroredTypeException e) {
                var declaredType = (DeclaredType) e.getTypeMirror();
                var typeElement = (TypeElement) declaredType.asElement();
                return typeElement.getQualifiedName().toString();
            }
        }
        return null;
    }

    public List<QualifierModel> extractQualifiers(Element element) {
        Set<QualifierModel> qualifiers = element.getAnnotationMirrors().stream()
                .map(this::convertAnnotationToQualifierModel)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(HashSet::new));
        var rawQualifiers = element.getAnnotationsByType(Qualifier.class);
        for (var q : rawQualifiers) {
            var m = new QualifierModel();
            m.setName(q.name());
            var propsMap = new HashMap<String, String>();
            for (var p : q.properties()) {
                propsMap.put(p.name(), p.value());
            }
            m.setProperties(propsMap);
            qualifiers.add(m);
        }
        return List.copyOf(qualifiers);
    }

    private QualifierModel convertAnnotationToQualifierModel(AnnotationMirror am) {
        Element annotationElement = typeUtils.asElement(am.getAnnotationType());
        Qualifier qualifier = annotationElement.getAnnotation(Qualifier.class);
        if (qualifier == null) {
            return null;
        }
        var result = new QualifierModel();
        result.setName(qualifier.name());
        if (qualifier.properties().length == 0) {
            return result;
        }
        var mappedNames = Stream.of(qualifier.properties())
                .collect(Collectors.toMap(QualifierProperty::name, p -> p.value().isBlank() ? p.name() : p.value()));
        var elementValuesWithDefaults = elementUtils.getElementValuesWithDefaults(am);
        var props = annotationElement.getEnclosedElements().stream()
                .filter(e -> e.getKind() == ElementKind.METHOD)
                .map(e -> (ExecutableElement) e)
                .filter(e -> mappedNames.containsKey(e.getSimpleName().toString()))
                .collect(Collectors.toMap(e -> mappedNames.get(e.getSimpleName().toString()), e -> annotationValueToString(elementValuesWithDefaults.get(e))));
        result.setProperties(props);
        return result;
    }

    private String annotationValueToString(AnnotationValue annotationValue) {
        return annotationValue.accept(annotationToStringVisitor, null);
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
