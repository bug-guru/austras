package guru.bug.austras.apt.core;

import guru.bug.austras.apt.core.componentmap.ComponentKey;
import guru.bug.austras.apt.core.componentmap.ComponentMap;
import guru.bug.austras.apt.core.componentmap.UniqueNameGenerator;
import guru.bug.austras.apt.core.generators.MainClassGenerator;
import guru.bug.austras.apt.model.ComponentModel;
import guru.bug.austras.apt.model.ProviderModel;
import guru.bug.austras.core.Application;
import guru.bug.austras.core.Component;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.StandardLocation;
import java.io.*;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.logging.Formatter;
import java.util.logging.*;
import java.util.stream.Collectors;

import static javax.lang.model.element.ElementKind.*;
import static javax.lang.model.element.Modifier.PUBLIC;

@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class AnnotationProcessorCore extends AbstractProcessor {
    private static final Logger log = Logger.getLogger(AnnotationProcessorCore.class.getName());
    private static final Set<Class<? extends Annotation>> supportedAnnotations = Set.of(Application.class, Component.class);
    private final List<ProviderModel> allProviders = new ArrayList<>();

    static {
        Logger logger = Logger.getLogger("guru.bug.austras");
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);
        var f = new Formatter() {
            @Override
            public String format(LogRecord record) {
                StringWriter sw = new StringWriter();
                var out = new PrintWriter(sw);

                String loggerName = record.getLoggerName();
                if (loggerName.length() > 25) {
                    loggerName = loggerName.substring(loggerName.length() - 25);
                }
                out.printf("[%-6s][%-25s] %s", record.getLevel(), loggerName, record.getMessage());
                if (record.getThrown() != null) {
                    out.print(" ");
                    record.getThrown().printStackTrace(out);
                }
                out.println();
                return sw.toString();
            }
        };
        FileOutputStream out;
        try {
            out = new FileOutputStream("austras.log");
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
        var h = new StreamHandler(out, f);
        h.setLevel(Level.ALL);
        logger.addHandler(h);
    }

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
        this.modelUtils = new ModelUtils(uniqueNameGenerator, processingEnv);
        this.componentMap = new ComponentMap();
        this.candidateComponentMap = new ComponentMap();
        this.mainClassGenerator = new MainClassGenerator(processingEnv, componentMap, uniqueNameGenerator);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            if (roundEnv.processingOver()) {
                ensureAllProviderSatisfiedDependencies();
                generateComponentMap();
                mainClassGenerator.generateAppMain();
            } else {
                var rootElements = roundEnv.getRootElements();
                scanRootElements(rootElements);
                resolveProviders();
                generateProviders();
            }
            return false;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Unexpected error", e);
            throw new IllegalStateException(e);
        }
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
        log.fine(() -> String.format("PROCESS: Found provider class %s.", element));
        providers.add(element);
    }

    private ComponentModel processAsComponent(Element element) {
        if (element.getKind() == CLASS) {
            TypeElement typeElement = (TypeElement) element;
            if (!checkIsPublicNonAbstractClass(typeElement)) {
                log.fine(() -> String.format("IGNORE: Found abstract or non-public class %s", element));
            } else if (!checkUsableConstructor(typeElement)) {
                log.fine(() -> String.format("IGNORE: No default constructor or multiple public constructors in class %s", element));
            } else {
                log.fine(() -> String.format("PROCESS: Found component class %s.", element));
                var model = modelUtils.createComponentModel((DeclaredType) typeElement.asType());
                var componentAnnotation = typeElement.getAnnotationsByType(Component.class);
                var applicationAnnotation = typeElement.getAnnotationsByType(Application.class);
                if (componentAnnotation.length == 0 && applicationAnnotation.length == 0) {
                    log.fine("...Adding to candidate components map");
                    candidateComponentMap.addComponent(model);
                } else {
                    log.fine("...Adding to components map");
                    componentMap.addComponent(model);
                }
                if (applicationAnnotation.length > 0) {
                    mainClassGenerator.setAppComponentModel(model);
                }
                return model;
            }
        } else if (element.getKind() == INTERFACE) {
            log.fine(() -> String.format("IGNORE: Found interface %s.", element));
        } else {
            log.fine(() -> String.format("IGNORE: Unknown element %s.", element));
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
        log.fine("Resolving providers");
        while (!providers.isEmpty()) {
            var providerElement = providers.remove();
            log.fine(() -> String.format("Resolving provider %s", providerElement));
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
                log.fine(() -> String.format("Provider %s provides non existing component of %s. Creating ComponentModel", providerModel.getInstantiable(), key));
                componentModel = modelUtils.createComponentModel(type, providerType);
                componentModel.setQualifiers(qualifiers);
                componentMap.addComponent(componentModel);
            }
            var cm = componentModel;
            log.fine(() -> String.format("Setting provider %s for component %s", providerElement, cm));
            componentModel.setProvider(providerModel);
            allProviders.add(providerModel);
        }
    }

    private void ensureAllProviderSatisfiedDependencies() {
        for (var p : allProviders) {
            ensureProviderSatisfiedDependencies(p);
        }
    }

    // TODO need to check all providers for satisfied dependencies before generating Main.
    private boolean ensureProviderSatisfiedDependencies(ProviderModel providerModel) {
        var result = true;
        for (var d : providerModel.getDependencies()) {
            var key = new ComponentKey(d.getType(), d.getQualifiers());
            if (!componentMap.hasComponent(key)) {
                var componentModels = candidateComponentMap.findComponentModels(key);
                if (componentModels.isEmpty()) {
                    result = false;
                }
                log.fine(() -> String.format("Provider %s: dependency component %s is resolved.", providerModel.getInstantiable(), key));
                componentMap.addComponents(componentModels);
            }
        }
        return result;
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
