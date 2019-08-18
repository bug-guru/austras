package guru.bug.austras.apt.core;

import guru.bug.austras.annotations.Application;
import guru.bug.austras.annotations.Component;
import guru.bug.austras.apt.core.componentmap.ComponentKey;
import guru.bug.austras.apt.core.componentmap.ComponentMap;
import guru.bug.austras.apt.core.componentmap.UniqueNameGenerator;
import guru.bug.austras.apt.core.generators.MainClassGenerator;
import guru.bug.austras.apt.model.ComponentModel;
import guru.bug.austras.apt.model.ProviderModel;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.lang.model.element.ElementKind.*;
import static javax.lang.model.element.Modifier.PUBLIC;

@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class AnnotationProcessorCore extends AbstractAustrasAnnotationProcessor {
    private static final Set<Class<? extends Annotation>> supportedAnnotations = Set.of(Application.class, Component.class);
    private final Queue<Element> providers = new LinkedList<>();
    private final UniqueNameGenerator uniqueNameGenerator = new UniqueNameGenerator();
    private ModelUtils modelUtils;
    private ComponentMap candidateComponentMap;
    private ComponentMap componentMap;
    private Elements elementUtils;
    private Types typeUtils;
    private MainClassGenerator mainClassGenerator;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.elementUtils = processingEnv.getElementUtils();
        this.typeUtils = processingEnv.getTypeUtils();
        this.modelUtils = new ModelUtils(this, uniqueNameGenerator, processingEnv);
        this.componentMap = new ComponentMap();
        this.candidateComponentMap = new ComponentMap();
        this.mainClassGenerator = new MainClassGenerator(this, processingEnv, componentMap, uniqueNameGenerator);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            generateComponentMap();
            mainClassGenerator.generateAppMain();
        } else {
            var rootElements = roundEnv.getRootElements();
            scanRootElements(rootElements);
            resolveProviders();
            generateProviders();
        }
        return false;
    }

    private void scanRootElements(Set<? extends Element> rootElements) {
        rootElements.forEach(this::scanElement);
    }

    private void scanElement(Element element) {
        if (modelUtils.isProvider(element)) {
            processProvider(element);
        } else {
            processAsComponent(element);
        }
    }

    private void processProvider(Element element) {
        debug("PROCESS: Found provider class %s.", element);
        providers.add(element);
    }

    private ComponentModel processAsComponent(Element element) {
        if (element.getKind() == CLASS) {
            TypeElement typeElement = (TypeElement) element;
            if (!checkIsPublicNonAbstractClass(typeElement)) {
                debug("IGNORE: Found abstract or non-public class %s", element);
            } else if (!checkUsableConstructor(typeElement)) {
                debug("IGNORE: No default constructor or multiple public constructors in class %s", element);
            } else {
                debug("PROCESS: Found component class %s.", element);
                var model = modelUtils.createComponentModel((DeclaredType) typeElement.asType());
                var componentAnnotation = typeElement.getAnnotationsByType(Component.class);
                var applicationAnnotation = typeElement.getAnnotationsByType(Application.class);
                if (componentAnnotation.length == 0 && applicationAnnotation.length == 0) {
                    debug("...Adding to candidate components map");
                    candidateComponentMap.addComponent(model);
                } else {
                    debug("...Adding to components map");
                    componentMap.addComponent(model);
                }
                if (applicationAnnotation.length > 0) {
                    mainClassGenerator.setAppComponentModel(model);
                }
                return model;
            }
        } else if (element.getKind() == INTERFACE) {
            debug("IGNORE: Found interface %s.", element);
        } else {
            debug("IGNORE: Unknown element %s.", element);
        }
        return null;
    }

    private boolean checkIsPublicNonAbstractClass(TypeElement typeElement) {
        var modifiers = typeElement.getModifiers();
        return !modifiers.contains(Modifier.ABSTRACT) && modifiers.contains(Modifier.PUBLIC);
    }

    private boolean checkUsableConstructor(TypeElement typeElement) {
        var constructors = typeElement.getEnclosedElements().stream()
                .filter(e -> e.getKind() == CONSTRUCTOR)
                .map(e -> (ExecutableElement) e)
                .collect(Collectors.toSet());
        if (constructors.isEmpty()) {
            return true;
        }
        var publicConstructors = constructors.stream()
                .filter(e -> e.getModifiers().contains(PUBLIC))
                .collect(Collectors.toSet());
        return publicConstructors.size() == 1;
    }

    private void generateComponentMap() {
        try (var out = processingEnv.getFiler().createResource(StandardLocation.SOURCE_OUTPUT, "", "META-INF/components.yml").openOutputStream();
             var w = new PrintWriter(out)) {
            componentMap.serialize(w);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void resolveProviders() {
        debug("Resolving providers");
        while (!providers.isEmpty()) {
            var providerElement = providers.remove();
            debug("Resolving provider %s", providerElement);
            DeclaredType providerType = (DeclaredType) providerElement.asType();
            var type = modelUtils.extractComponentTypeFromProvider(providerType);
            var qualifiers = modelUtils.extractQualifiers(providerElement);
            var key = new ComponentKey(type.toString(), qualifiers);
            var componentModel = componentMap.findSingleComponentModel(key);
            if (componentModel != null && componentModel.getProvider() != null) {
                throw new IllegalStateException("Provider already set");
            }
            var name = uniqueNameGenerator.findFreeVarName(providerType);
            var dependencies = modelUtils.collectConstructorParams(providerType);
            var instantiable = providerElement.toString();

            var providerModel = new ProviderModel();
            providerModel.setInstantiable(instantiable);
            providerModel.setName(name);
            providerModel.setDependencies(dependencies);

            if (componentModel == null) {
                debug("Provider %s provides non existing component of %s. Creating ComponentModel", providerModel.getInstantiable(), key);
                componentModel = modelUtils.createComponentModel(type, providerType);
                componentMap.addComponent(componentModel);
            }
            debug("Setting provider %s for component %s", providerElement, componentModel);
            componentModel.setProvider(providerModel);
            componentModel.setQualifiers(qualifiers);
            ensureProviderSatisfiedDependencies(providerModel);
        }
    }

    private void ensureProviderSatisfiedDependencies(ProviderModel providerModel) {
        for (var d : providerModel.getDependencies()) {
            var paramVarElement = d.getParamElement();
            var paramType = (DeclaredType) d.getParamElement().asType();
            DeclaredType componentType;
            boolean isProvider = modelUtils.isProvider(paramType);
            if (isProvider) {
                componentType = modelUtils.extractComponentTypeFromProvider(paramType);
            } else {
                componentType = paramType;
            }
            boolean isCollection = modelUtils.isCollection(componentType);
            if (isCollection) {
                componentType = modelUtils.extractComponentTypeFromCollection(componentType);
            }
            var key = new ComponentKey(componentType.toString(), d.getQualifiers());
            if (!componentMap.hasComponent(key)) {
                var componentModels = candidateComponentMap.findComponentModels(key);
                if (componentModels.isEmpty()) {
                    throw new IllegalStateException("Provider " + providerModel.getInstantiable() + " Unresolved dependency: " + key);
                }
                debug("Provider %s: dependency component %s is resolved.", providerModel.getInstantiable(), key);
                componentMap.addComponents(componentModels);
            }
        }
    }

    private void generateProviders() {
        componentMap.allComponentsStream()
                .filter(cm -> cm.getProvider() == null)
                .forEach(cm -> {
                    var providerGenerator = modelUtils.createProviderGeneratorFor(cm);
                    providerGenerator.generateProvider();
                });
    }

}
