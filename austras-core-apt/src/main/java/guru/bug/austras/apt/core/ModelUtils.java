package guru.bug.austras.apt.core;

import guru.bug.austras.apt.core.common.model.*;
import guru.bug.austras.core.Selector;
import guru.bug.austras.core.qualifiers.Qualifier;
import guru.bug.austras.core.qualifiers.QualifierProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleAnnotationValueVisitor9;
import javax.lang.model.util.TypeKindVisitor9;
import javax.lang.model.util.Types;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javax.lang.model.element.Modifier.PUBLIC;

// TODO try to move all methods to related classes
public class ModelUtils {
    private static final Logger log = LoggerFactory.getLogger(ModelUtils.class);
    @SuppressWarnings("squid:MaximumInheritanceDepth")
    private static final AnnotationValueVisitor<String, Void> annotationToStringVisitor = new SimpleAnnotationValueVisitor9<>() {
        @Override
        protected String defaultAction(Object o, Void aVoid) {
            return Objects.toString(o);
        }
    };
    private final UniqueNameGenerator uniqueNameGenerator;
    private final Types typeUtils;
    private final Elements elementUtils;
    private final TypeElement selectorInterfaceElement;
    private final DeclaredType selectorInterfaceType;
    private final TypeElement collectionInterfaceElement;
    private final DeclaredType collectionInterfaceType;

    public ModelUtils(UniqueNameGenerator uniqueNameGenerator, ProcessingEnvironment processingEnv) {
        this.uniqueNameGenerator = uniqueNameGenerator;
        this.typeUtils = processingEnv.getTypeUtils();
        this.elementUtils = processingEnv.getElementUtils();
        this.selectorInterfaceElement = elementUtils.getTypeElement(Selector.class.getName());
        this.selectorInterfaceType = typeUtils.getDeclaredType(selectorInterfaceElement);
        this.collectionInterfaceElement = elementUtils.getTypeElement(Collection.class.getName());
        this.collectionInterfaceType = typeUtils.getDeclaredType(collectionInterfaceElement);
    }

    // TODO right place of this method is - ComponentModel
    public ComponentModel createComponentModel(TypeElement type) {
        return createComponentModel((DeclaredType) type.asType(), type);
    }

    // TODO right place of this method is - ComponentModel
    public ComponentModel createComponentModel(DeclaredType type, AnnotatedConstruct metaInfo) {
        var ancestors = collectAllAncestor(type).stream()
                .map(TypeMirror::toString)
                .collect(Collectors.toList());
        ancestors.add(type.toString());
        log.debug("All superclasses and interfaces: {}", ancestors);
        var varName = uniqueNameGenerator.findFreeVarName(type);
        var qualifiers = extractQualifiers(metaInfo);

        return ComponentModel.builder()
                .qualifiers(qualifiers)
                .instantiable(type.toString())
                .name(varName)
                .types(ancestors)
                .build();
    }

    // TODO right place of this method is - QualifierSetModel
    public QualifierSetModel extractQualifiers(AnnotatedConstruct element) {
        var result = QualifierSetModel.builder();
        for (var a : element.getAnnotationMirrors()) {
            var qualifierModel = convertAnnotationToQualifierModel(a);
            if (qualifierModel != null) {
                result.addQualifier(qualifierModel);
            }
        }
        for (var e : element.getAnnotationsByType(Qualifier.class)) {
            var qualifierModel = convertRawQualifierToModel(e);
            if (qualifierModel != null) {
                result.addQualifier(qualifierModel);
            }
        }
        return result.build();
    }

    // TODO right place of this method is - QualifierSetModel
    private QualifierModel convertRawQualifierToModel(Qualifier q) {
        var qualifierBuilder = QualifierModel.builder();
        qualifierBuilder.name(q.name());
        for (var p : q.properties()) {
            var property = QualifierPropertyModel.builder()
                    .name(p.name())
                    .value(p.value())
                    .build();
            qualifierBuilder.addProperty(property);
        }
        return qualifierBuilder.build();
    }

    // TODO right place of this method is - QualifierSetModel
    private QualifierModel convertAnnotationToQualifierModel(AnnotationMirror am) {
        // TODO need to check whether typeUtils is required. Seems same effect can be done useing am.getAnnotationType().asElement()
        //      if so - move extractQualifiers (and all related methods) to QualifierSetModel
        Element annotationElement = typeUtils.asElement(am.getAnnotationType());
        Qualifier qualifier = annotationElement.getAnnotation(Qualifier.class);
        if (qualifier == null) {
            return null;
        }
        var qualifierBuilder = QualifierModel.builder();
        qualifierBuilder.name(qualifier.name());

        if (qualifier.properties().length == 0) {
            return qualifierBuilder.build();
        }

        var mappedNames = Stream.of(qualifier.properties())
                .collect(Collectors.toMap(QualifierProperty::name, p -> p.value().isBlank() ? p.name() : p.value()));
        var elementValuesWithDefaults = elementUtils.getElementValuesWithDefaults(am);
        annotationElement.getEnclosedElements().stream()
                .filter(e -> e.getKind() == ElementKind.METHOD)
                .map(e -> (ExecutableElement) e)
                .filter(e -> mappedNames.containsKey(e.getSimpleName().toString()))
                .forEach(e -> {
                    var name = mappedNames.get(e.getSimpleName().toString());
                    var value = annotationValueToString(elementValuesWithDefaults.get(e));
                    var property = QualifierPropertyModel.builder()
                            .name(name)
                            .value(value)
                            .build();
                    qualifierBuilder.addProperty(property);
                });
        return qualifierBuilder.build();
    }

    private String annotationValueToString(AnnotationValue annotationValue) {
        return annotationValue.accept(annotationToStringVisitor, null);
    }

    private Set<DeclaredType> collectAllAncestor(DeclaredType componentType) {
        var result = new HashSet<DeclaredType>();
        var checked = new HashSet<TypeMirror>();
        var toCheck = new LinkedList<TypeMirror>(typeUtils.directSupertypes(componentType));
        while (!toCheck.isEmpty()) {
            var cur = toCheck.remove();
            if (isClassOrInterface(cur) && checked.add(cur)) {
                var curType = (DeclaredType) cur;
                result.add(curType);
                var supertypes = typeUtils.directSupertypes(curType);
                log.trace("Adding supertypes to check-queue: {}", supertypes);
                toCheck.addAll(supertypes);
            }
        }
        return result;
    }

    private boolean isClassOrInterface(TypeMirror type) {
        if (type.getKind() != TypeKind.DECLARED) {
            return false;
        }
        var elem = ((DeclaredType) type).asElement();
        return elem.getKind() == ElementKind.CLASS || elem.getKind() == ElementKind.INTERFACE;
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
        var componentType = tmp.get(0).accept(new TypeKindVisitor9<DeclaredType, Void>() { // NOSONAR TypeKindVisitor9 has deep inheritance. Nothing I can do
            @Override
            public DeclaredType visitDeclared(DeclaredType t, Void aVoid) {
                return t;
            }

            @Override
            public DeclaredType visitWildcard(WildcardType t, Void aVoid) {
                return (DeclaredType) t.getExtendsBound();
            }
        }, null);
        log.debug("Wrapper {} wraps component: {}", wrapperType, componentType);
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

    public DependencyModel createDependencyModel(DeclaredType paramType, AnnotatedConstruct metadata) {
        var resultBuilder = DependencyModel.builder();
        var qualifiers = extractQualifiers(metadata);

        var isCollection = typeUtils.isAssignable(paramType, collectionInterfaceType);
        var isSelector = typeUtils.isAssignable(paramType, selectorInterfaceType);

        if (isCollection) {
            paramType = unwrap(paramType, collectionInterfaceElement);
            resultBuilder.wrapping(WrappingType.COLLECTION);
        } else if (isSelector) {
            paramType = unwrap(paramType, selectorInterfaceElement);
            resultBuilder.wrapping(WrappingType.SELECTOR);
        }

        resultBuilder.type(paramType.toString());
        resultBuilder.qualifiers(qualifiers);

        return resultBuilder.build();
    }

    public DependencyModel createDependencyModel(VariableElement paramElement) {
        var paramType = (DeclaredType) paramElement.asType();
        return createDependencyModel(paramType, paramElement);
    }

}
