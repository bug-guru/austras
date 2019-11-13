package guru.bug.austras.apt.core;

import guru.bug.austras.apt.core.componentmap.UniqueNameGenerator;
import guru.bug.austras.apt.core.generators.CacheProviderGenerator;
import guru.bug.austras.apt.core.generators.EagerSingletonProviderGenerator;
import guru.bug.austras.apt.core.generators.NoCacheProviderGenerator;
import guru.bug.austras.apt.core.generators.ProviderGenerator;
import guru.bug.austras.apt.model.ComponentModel;
import guru.bug.austras.apt.model.DependencyModel;
import guru.bug.austras.apt.model.QualifierModel;
import guru.bug.austras.core.Qualifier;
import guru.bug.austras.core.QualifierProperty;
import guru.bug.austras.events.Broadcaster;
import guru.bug.austras.provider.Provider;
import guru.bug.austras.scopes.Cache;
import guru.bug.austras.scopes.NoCache;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleAnnotationValueVisitor9;
import javax.lang.model.util.TypeKindVisitor9;
import javax.lang.model.util.Types;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javax.lang.model.element.Modifier.PUBLIC;

public class ModelUtils {
    private static final Logger log = Logger.getLogger(ModelUtils.class.getName());
    private final static AnnotationValueVisitor<String, Void> annotationToStringVisitor = new SimpleAnnotationValueVisitor9<>() {
        @Override
        protected String defaultAction(Object o, Void aVoid) {
            return Objects.toString(o);
        }
    };
    private final UniqueNameGenerator uniqueNameGenerator;
    private final Types typeUtils;
    private final Elements elementUtils;
    private final ProcessingEnvironment processingEnv;
    private final String componentCacheVarName;
    private final TypeElement collectionInterfaceElement;
    private final DeclaredType collectionInterfaceType;
    private final TypeElement providerInterfaceElement;
    private final DeclaredType providerInterfaceType;
    private final TypeElement broadcasterInterfaceElement;
    private final DeclaredType broadcasterInterfaceType;

    public ModelUtils(UniqueNameGenerator uniqueNameGenerator, ProcessingEnvironment processingEnv) {
        this.uniqueNameGenerator = uniqueNameGenerator;
        this.processingEnv = processingEnv;
        this.typeUtils = processingEnv.getTypeUtils();
        this.elementUtils = processingEnv.getElementUtils();
        this.componentCacheVarName = uniqueNameGenerator.findFreeVarName("componentCache");
        this.collectionInterfaceElement = elementUtils.getTypeElement(Collection.class.getName());
        this.collectionInterfaceType = typeUtils.getDeclaredType(collectionInterfaceElement);
        this.providerInterfaceElement = elementUtils.getTypeElement(Provider.class.getName());
        this.providerInterfaceType = typeUtils.getDeclaredType(providerInterfaceElement);
        this.broadcasterInterfaceElement = elementUtils.getTypeElement(Broadcaster.class.getName());
        this.broadcasterInterfaceType = typeUtils.getDeclaredType(broadcasterInterfaceElement);
    }

    public ComponentModel createComponentModel(TypeElement type) {
        return createComponentModel((DeclaredType) type.asType(), type);
    }

    public ComponentModel createComponentModel(DeclaredType type, AnnotatedConstruct metaInfo) {
        var ancestors = collectAllAncestor(type).stream()
                .map(TypeMirror::toString)
                .collect(Collectors.toList());
        ancestors.add(type.toString());
        log.fine(() -> String.format("All superclasses and interfaces: %s", ancestors));
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
        var cachedAnnotation = componentElement.getAnnotation(Cache.class);
        var cacheType = extractValueFromCachedAnnotation(cachedAnnotation);
        var noCachedAnnotation = componentElement.getAnnotation(NoCache.class);
        List<DependencyModel> dependencies = collectConstructorParams(componentElement);
        if (cachedAnnotation == null && noCachedAnnotation == null) {
            return new EagerSingletonProviderGenerator(processingEnv, componentModel, dependencies);
        } else if (noCachedAnnotation != null) {
            return new NoCacheProviderGenerator(processingEnv, componentModel, dependencies);
        } else {
            return new CacheProviderGenerator(processingEnv, componentModel, dependencies, cacheType, componentCacheVarName);
        }
    }

    private String extractValueFromCachedAnnotation(Cache cacheAnnotation) {
        if (cacheAnnotation != null) {
            try {
                cacheAnnotation.value();
            } catch (MirroredTypeException e) {
                var declaredType = (DeclaredType) e.getTypeMirror();
                var typeElement = (TypeElement) declaredType.asElement();
                return typeElement.getQualifiedName().toString();
            }
        }
        return null;
    }

    public QualifierModel extractQualifiers(AnnotatedConstruct element) {
        var result = new QualifierModel();
        for (var a : element.getAnnotationMirrors()) {
            convertAnnotationToQualifierModel(a, result);
        }
        for (var e : element.getAnnotationsByType(Qualifier.class)) {
            convertRawQualifierToModel(e, result);
        }
        return result;
    }

    private void convertRawQualifierToModel(Qualifier q, QualifierModel result) {
        String name = q.name();
        result.setProperty(name);
        for (var p : q.properties()) {
            result.setProperty(name, p.name(), p.value());
        }
    }

    private void convertAnnotationToQualifierModel(AnnotationMirror am, QualifierModel result) {
        Element annotationElement = typeUtils.asElement(am.getAnnotationType());
        Qualifier qualifier = annotationElement.getAnnotation(Qualifier.class);
        if (qualifier == null) {
            return;
        }
        String name = qualifier.name();
        result.setProperty(name);
        if (qualifier.properties().length == 0) {
            return;
        }
        var mappedNames = Stream.of(qualifier.properties())
                .collect(Collectors.toMap(QualifierProperty::name, p -> p.value().isBlank() ? p.name() : p.value()));
        var elementValuesWithDefaults = elementUtils.getElementValuesWithDefaults(am);
        annotationElement.getEnclosedElements().stream()
                .filter(e -> e.getKind() == ElementKind.METHOD)
                .map(e -> (ExecutableElement) e)
                .filter(e -> mappedNames.containsKey(e.getSimpleName().toString()))
                .forEach(e -> {
                    var key = mappedNames.get(e.getSimpleName().toString());
                    var val = annotationValueToString(elementValuesWithDefaults.get(e));
                    result.setProperty(name, key, val);
                });
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
            log.fine(() -> String.format("Checking %s", cur));
            if (checked.contains(cur)) {
                log.fine("Already checked");
                continue;
            }
            checked.add(cur);
            if (cur.getKind() != TypeKind.DECLARED) {
                log.fine(() -> String.format("%s is not a declared type", cur));
                continue;
            }
            var curType = ((DeclaredType) cur);
            var curElem = curType.asElement();
            if (curElem.getKind() != ElementKind.CLASS && curElem.getKind() != ElementKind.INTERFACE) {
                log.fine(() -> String.format("%s is not an interface or class", curElem));
                continue;
            }
            var curDeclElem = (TypeElement) curElem;
            result.add(curType);
            var supertypes = typeUtils.directSupertypes(curType);
            log.fine(() -> String.format("adding supertypes to check-queue: %s", supertypes));
            toCheck.addAll(supertypes);
        }
        return result;
    }

    public DeclaredType extractComponentTypeFromProvider(DeclaredType providerType) {
        if (!isProvider(providerType)) {
            throw new IllegalArgumentException(providerType + " isn't a provider");
        }
        return unwrap(providerType, providerInterfaceElement);
    }

    public DeclaredType extractComponentTypeFromCollection(DeclaredType collectionType) {
        if (!isCollection(collectionType)) {
            throw new IllegalArgumentException(collectionType + " isn't a collection");
        }
        return unwrap(collectionType, collectionInterfaceElement);
    }

    public DeclaredType extractComponentTypeFromBroadcaster(DeclaredType broadcasterType) {
        if (!isBroadcaster(broadcasterType)) {
            throw new IllegalArgumentException(broadcasterType + " isn't a broadcaster");
        }
        return unwrap(broadcasterType, broadcasterInterfaceElement);
    }

    private DeclaredType unwrap(DeclaredType wrapperType, Element wrapperElement) {
        var tmp = Stream.concat(collectAllAncestor(wrapperType).stream(), Stream.of(wrapperType))
                .filter(dt -> dt.asElement().equals(wrapperElement))
                .map(dt -> dt.getTypeArguments().get(0))
                .distinct()
                .collect(Collectors.toList());
        if (tmp.size() != 1) {
            throw new IllegalArgumentException("Not expected count of type parameters: " + tmp);
        }
        var componentType = tmp.get(0).accept(new TypeKindVisitor9<DeclaredType, Void>() {
            @Override
            public DeclaredType visitDeclared(DeclaredType t, Void aVoid) {
                return t;
            }

            @Override
            public DeclaredType visitWildcard(WildcardType t, Void aVoid) {
                return (DeclaredType) t.getExtendsBound();
            }
        }, null);
        log.fine(() -> String.format("Wrapper %s wraps component: %s", wrapperType, componentType));
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

    public DependencyModel createDependencyModel(String varName, DeclaredType paramType, AnnotatedConstruct metadata) {
        var result = new DependencyModel();
        var qualifiers = extractQualifiers(metadata);

        boolean isProvider = isProvider(paramType);
        if (isProvider) {
            paramType = extractComponentTypeFromProvider(paramType);
        }

        var isCollection = isCollection(paramType);
        if (isCollection) {
            paramType = extractComponentTypeFromCollection(paramType);
        }

        result.setProvider(isProvider);
        result.setCollection(isCollection);
        result.setType(paramType.toString());
        result.setName(varName);
        result.setQualifiers(qualifiers);

        return result;
    }

    public DependencyModel createDependencyModel(VariableElement paramElement) {

        var paramType = (DeclaredType) paramElement.asType();
        var varName = paramElement.getSimpleName().toString();
        return createDependencyModel(varName, paramType, paramElement);
    }

    public boolean isBroadcaster(TypeMirror type) {
        return typeUtils.isAssignable(type, broadcasterInterfaceType);
    }

    public boolean isCollection(DeclaredType type) {
        return typeUtils.isAssignable(type, collectionInterfaceType);
    }

    public boolean isProvider(TypeMirror type) {
        return typeUtils.isAssignable(type, providerInterfaceType);
    }

    public boolean isProvider(Element element) {
        return isProvider(element.asType());
    }

    public String qualifierToString(QualifierModel qualifierModel) {
        var result = new StringBuilder(512);
        qualifierModel.forEach((qualifierName, properties) -> {
            result.append("@")
                    .append(Qualifier.class.getSimpleName())
                    .append("(name = \"")
                    .append(qualifierName)
                    .append("\"");
            if (!properties.isEmpty()) {
                result.append(", properties = ");
                if (properties.size() > 1) {
                    result.append("{");
                }
                result.append(properties.stream()
                        .map(p -> String.format("@QualifierProperty(name = \"%s\", value = \"%s\"", p.getKey(), p.getValue()))
                        .collect(Collectors.joining(", "))
                );
                if (properties.size() > 1) {
                    result.append("}");
                }
            }
            result.append(") ");
        });
        return result.toString();
    }
}
