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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.logging.*;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static javax.lang.model.element.ElementKind.CLASS;
import static javax.lang.model.element.ElementKind.CONSTRUCTOR;
import static javax.lang.model.element.Modifier.PUBLIC;

@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class AnnotationProcessorCore extends AbstractProcessor {
    private static final Logger log = Logger.getLogger(AnnotationProcessorCore.class.getName());

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

    private final Queue<TypeElement> stagedProviders = new LinkedList<>();
    private final UniqueNameGenerator uniqueNameGenerator = new UniqueNameGenerator();
    private ModelUtils modelUtils;
    private ComponentMap stagedComponents;
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
        this.stagedComponents = new ComponentMap();
        this.mainClassGenerator = new MainClassGenerator(processingEnv, componentMap, uniqueNameGenerator);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            if (roundEnv.processingOver()) {
//                ensureAllProviderSatisfiedDependencies();
                generateComponentMap();
                mainClassGenerator.generateAppMain();
            } else {
                log.fine("SCAN");
                var rootElements = roundEnv.getRootElements();
                scanRootElements(rootElements);
                log.fine("LINK");
                linkProviders();
                log.fine("RESOLVE AND GENERATE");
                resolveAndGenerateProviders();
            }
            return false;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Unexpected error", e);
            throw new IllegalStateException(e);
        }
    }

    private void scanRootElements(Set<? extends Element> rootElements) {
        for (var element : rootElements) {
            if (element.getKind() != CLASS) {
                log.fine(() -> format("IGNORE: root element isn't a class: %s", element));
                continue;
            }
            TypeElement typeElement = (TypeElement) element;
            if (!checkIsPublicNonAbstractClass(typeElement)) {
                log.info(() -> format("IGNORE: Found abstract or non-public class %s", element));
                continue;
            }
            if (!checkUsableConstructor(typeElement)) {
                log.info(() -> format("IGNORE: No default constructor or multiple public constructors in class %s", element));
                continue;
            }
            if (modelUtils.isProvider(element)) {
                log.info(() -> format("PROCESS: Found provider class %s.", element));
                stagedProviders.add(typeElement);
            } else {
                log.info(() -> format("PROCESS: Found component class %s.", element));
                scanComponent(typeElement);
            }
        }
    }

    private void scanComponent(TypeElement typeElement) {
        var model = modelUtils.createComponentModel(typeElement);
        var componentAnnotation = typeElement.getAnnotationsByType(Component.class);
        var applicationAnnotation = typeElement.getAnnotationsByType(Application.class);
        if (componentAnnotation.length == 0 && applicationAnnotation.length == 0) {
            log.fine(() -> format("PROCESS: Adding component to staging: %s", typeElement));
            stagedComponents.addComponent(model);
        } else {
            log.fine(() -> format("PROCESS: Adding component to index: %s", typeElement));
            componentMap.addComponent(model);
        }
        if (applicationAnnotation.length > 0) {
            mainClassGenerator.setAppComponentModel(model);
        }
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

    private void linkProviders() {
        if (stagedProviders.isEmpty()) {
            log.info("No providers found for linking");
            return;
        } else {
            log.fine(() -> format("Linking %d providers", stagedProviders.size()));
        }
        while (!stagedProviders.isEmpty()) {
            log.info(() -> format("%d providers yet to link", stagedProviders.size()));
            var providerElement = stagedProviders.remove();
            log.info(() -> format("Link provider %s", providerElement));
            DeclaredType providerType = (DeclaredType) providerElement.asType();
            var componentType = modelUtils.extractComponentTypeFromProvider(providerType);
            var qualifiers = modelUtils.extractQualifiers(providerElement);
            var key = new ComponentKey(componentType.toString(), qualifiers);

            var models = stagedComponents.findAndRemoveComponentModels(key);
            componentMap.addComponents(models);

            var componentModel = componentMap.findSingleComponentModel(key);
            if (componentModel != null && componentModel.getProvider() != null) {
                var msg = format("CONFLICT: provider %s cannot be set for the component %s. Provider already set: %s",
                        providerElement,
                        componentModel.getInstantiable(),
                        componentModel.getProvider().getInstantiable());
                log.log(Level.SEVERE, msg);
                throw new IllegalStateException(msg); // TODO better error handling
            }
            var name = uniqueNameGenerator.findFreeVarName(providerType);
            var dependencies = modelUtils.collectConstructorParams(providerType);
            var instantiable = providerElement.toString();

            var providerModel = new ProviderModel();
            providerModel.setInstantiable(instantiable);
            providerModel.setName(name);
            providerModel.setDependencies(dependencies);

            if (componentModel == null) {
                log.fine(() -> format("Provider %s provides non existing component of %s. Creating ComponentModel", providerModel.getInstantiable(), key));
                componentModel = modelUtils.createComponentModel(componentType, providerType);
                componentModel.setQualifiers(qualifiers);
                componentMap.addComponent(componentModel);
            }
            log.info(() -> format("Assigning provider %s for component %s", providerElement, componentType));
            componentModel.setProvider(providerModel);
        }
    }

    private void resolveAndGenerateProviders() {
        var toAdd = new ArrayList<ComponentModel>();
        componentMap.allComponentsStream()
                .forEach(cm -> {
                    var provider = cm.getProvider();
                    if (provider == null) {
                        log.info(() -> format("<1>Component %s doesn't have a provider yet. Generating.", cm.getInstantiable()));
                        generateProvider(cm);
                    } else {
                        log.info(() -> format("Resolving dependencies of provider %s (component %s)", provider.getInstantiable(), cm.getInstantiable()));
                        for (var d : provider.getDependencies()) {
                            log.fine(() -> format("resolving %s of type %s", d.getName(), d.getType()));
                            var k = new ComponentKey(d.getType(), d.getQualifiers());
                            var components = stagedComponents.findAndRemoveComponentModels(k);
                            toAdd.addAll(components);
                        }
                    }
                });
        componentMap.addComponents(toAdd);
        for (var c : toAdd) {
            log.info(() -> format("<2>Component %s doesn't have a provider yet. Generating.", c.getInstantiable()));
            generateProvider(c);
        }
    }

//    private void ensureAllProviderSatisfiedDependencies() {
//        for (var p : allProviders) {
//            ensureProviderSatisfiedDependencies(p);
//        }
//    }

    // TODO need to check all providers for satisfied dependencies before generating Main.
    private boolean ensureProviderSatisfiedDependencies(ProviderModel providerModel) {
        var result = true;
        for (var d : providerModel.getDependencies()) {
            var key = new ComponentKey(d.getType(), d.getQualifiers());
            if (!componentMap.hasComponent(key)) {
                var componentModels = stagedComponents.findAndRemoveComponentModels(key);
                if (componentModels.isEmpty()) {
                    result = false;
                }
                log.fine(() -> format("Provider %s: dependency component %s is resolved.", providerModel.getInstantiable(), key));
                componentMap.addComponents(componentModels);
                // FIXME AFTER THIS NO MORE CODE GENERATION IS EXECUTED, BUT PROVIDERS ARE NOT GENERATED FOR CANDIDATES
            }
        }
        return result;
    }

    private void generateProvider(ComponentModel model) {
        var providerGenerator = modelUtils.createProviderGeneratorFor(model);
        providerGenerator.generateProvider();
    }

}
