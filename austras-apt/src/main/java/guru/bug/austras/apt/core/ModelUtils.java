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
import guru.bug.austras.provider.Provider;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleAnnotationValueVisitor9;
import javax.lang.model.util.TypeKindVisitor9;
import javax.lang.model.util.Types;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private final DeclaredType collectionInterfaceType;
    private final ProcessingEnvironment processingEnv;
    private final String componentCacheVarName;
    private final Element collectionInterfaceElement;
    private final Element providerInterfaceElement;

    public ModelUtils(Logger log, UniqueNameGenerator uniqueNameGenerator, ProcessingEnvironment processingEnv) {
        this.log = log;
        this.uniqueNameGenerator = uniqueNameGenerator;
        this.processingEnv = processingEnv;
        this.typeUtils = processingEnv.getTypeUtils();
        this.elementUtils = processingEnv.getElementUtils();
        this.componentCacheVarName = uniqueNameGenerator.findFreeVarName("componentCache");
        this.providerInterfaceType = typeUtils.getDeclaredType(elementUtils.getTypeElement(Provider.class.getName()));
        this.collectionInterfaceType = typeUtils.getDeclaredType(elementUtils.getTypeElement(Collection.class.getName()));
        this.collectionInterfaceElement = collectionInterfaceType.asElement();
        this.providerInterfaceElement = providerInterfaceType.asElement();
    }

    public ComponentModel createComponentModel(DeclaredType type) {
        return createComponentModel(type, type);
    }

    public ComponentModel createComponentModel(DeclaredType type, DeclaredType metaInfo) {
        var ancestors = collectAllAncestor(type).stream()
                .map(TypeMirror::toString)
                .collect(Collectors.toList());
        ancestors.add(type.toString());
        log.debug("All superclasses and interfaces: %s", ancestors);
        var varName = uniqueNameGenerator.findFreeVarName(type);
        var qualifiers = extractQualifiers(metaInfo);
        var model = new ComponentModel(type);

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

    public List<QualifierModel> extractQualifiers(AnnotatedConstruct element) {
        Set<QualifierModel> qualifiers = element.getAnnotationMirrors().stream()
                .map(this::convertAnnotationToQualifierModel)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(HashSet::new));
        Arrays.stream(element.getAnnotationsByType(Qualifier.class))
                .map(this::convertRawQualifierToModel)
                .collect(Collectors.toCollection(() -> qualifiers));
        return List.copyOf(qualifiers);
    }

    private QualifierModel convertRawQualifierToModel(Qualifier q) {
        var m = new QualifierModel();
        m.setName(q.name());
        var propsMap = new HashMap<String, String>();
        for (var p : q.properties()) {
            propsMap.put(p.name(), p.value());
        }
        m.setProperties(propsMap);
        return m;
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
                log.debug("Already checked");
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
        log.debug("Wrapper %s wraps component: %s", wrapperType, componentType);
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
        var result = new DependencyModel();

        var paramType = (DeclaredType) paramElement.asType();
        var qualifiers = extractQualifiers(paramElement);
        var varName = paramElement.getSimpleName().toString();
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
        result.setParamElement(paramElement);
        return result;
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
}
