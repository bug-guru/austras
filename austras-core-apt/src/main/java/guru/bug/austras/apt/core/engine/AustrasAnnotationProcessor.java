package guru.bug.austras.apt.core.engine;

import guru.bug.austras.apt.core.ComponentMap;
import guru.bug.austras.apt.core.ModelUtils;
import guru.bug.austras.apt.core.UniqueNameGenerator;
import guru.bug.austras.apt.core.model.*;
import guru.bug.austras.apt.core.process.DefaultProviderGenerator;
import guru.bug.austras.apt.core.process.MainClassGenerator;
import guru.bug.austras.apt.core.process.ModuleModelSerializer;
import guru.bug.austras.codegen.TemplateException;
import guru.bug.austras.core.Application;
import guru.bug.austras.core.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.*;
import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static javax.lang.model.element.ElementKind.CLASS;
import static javax.lang.model.element.ElementKind.CONSTRUCTOR;
import static javax.lang.model.element.Modifier.PUBLIC;

@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class AustrasAnnotationProcessor extends AbstractProcessor {
    private static final Logger log = LoggerFactory.getLogger(AustrasAnnotationProcessor.class);

    private final Queue<TypeElement> stagedProviders = new LinkedList<>();
    private final UniqueNameGenerator uniqueNameGenerator = new UniqueNameGenerator();
    private List<AustrasProcessorPlugin> plugins;
    private ModelUtils modelUtils;
    private MainClassGenerator mainClassGenerator;
    private DefaultProviderGenerator defaultProviderGenerator;
    private ComponentMap stagedComponents;
    private ComponentMap componentMap;
    private ComponentModel appMainComponent;

    public AustrasAnnotationProcessor() {
        log.debug("Constructing AustrasAnnotationProcessor");
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        log.debug("initializing AustrasAnnotationProcessor");
        super.init(processingEnv);
        this.modelUtils = new ModelUtils(uniqueNameGenerator, processingEnv);

        this.componentMap = new ComponentMap();
        this.stagedComponents = new ComponentMap();
        readComponentMaps();
        initPlugins();
        try {
            this.mainClassGenerator = new MainClassGenerator(processingEnv.getFiler());
            this.defaultProviderGenerator = new DefaultProviderGenerator(processingEnv);
        } catch (IOException | TemplateException e) {
            throw new IllegalStateException(e);
        }
    }

    private void initPlugins() {
        var loader = ServiceLoader.load(AustrasProcessorPlugin.class, this.getClass().getClassLoader());
        this.plugins = loader.stream().map(ServiceLoader.Provider::get).collect(Collectors.toList());
    }

    private void readComponentMaps() {
        try {
            var classLoader = this.getClass().getClassLoader();
            var allMaps = classLoader.getResources("META-INF/components.yml");
            while (allMaps.hasMoreElements()) {
                var map = allMaps.nextElement();
                log.info("Loading components from {}", map);
                try (var stream = map.openStream()) {
                    var moduleModel = ModuleModelSerializer.load(stream);
                    for (var comp : moduleModel.getComponents()) {
                        comp.setImported(true);
                        componentMap.addComponent(comp);
                    }
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException(e); // TODO
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        var pctx = new ProcessingContextImpl() {

            @Override
            public RoundEnvironment roundEnv() {
                return roundEnv;
            }
        };
        try {
            if (roundEnv.processingOver()) {
                generateComponentMap();
                mainClassGenerator.generateAppMain(appMainComponent, componentMap);
            } else {
                log.debug("SCAN");
                var rootElements = roundEnv.getRootElements();
                scanRootElements(rootElements);
                log.debug("LINK");
                linkProviders();
                log.debug("RESOLVE AND GENERATE");
                resolveAndGenerateProviders();
                log.debug("PLUGINS");
                processPlugins(pctx);
            }
            return false;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private void processPlugins(ProcessingContext ctx) {
        for (var p : plugins) {
            log.debug("Processing {}", p.getClass().getName());
            p.process(ctx);
        }
    }

    private void scanRootElements(Set<? extends Element> rootElements) {
        for (var element : rootElements) {
            if (shouldBeIgnored(element)) {
                continue;
            }
            TypeElement typeElement = (TypeElement) element;
            if (modelUtils.isProvider(element)) {
                log.info("PROCESS: Found provider class {}.", element);
                stagedProviders.add(typeElement);
            } else {
                log.info("PROCESS: Found component class {}.", element);
                scanComponent(typeElement);
            }
        }
    }

    private boolean shouldBeIgnored(Element element) {
        if (element.getKind() != CLASS) {
            log.debug("IGNORE: root element isn't a class: {}", element);
            return true;
        }
        TypeElement typeElement = (TypeElement) element;
        if (!checkIsPublicNonAbstractClass(typeElement)) {
            log.info("IGNORE: Found abstract or non-public class {}", element);
            return true;
        }
        if (!checkUsableConstructor(typeElement)) {
            log.info("IGNORE: No default constructor or multiple public constructors in class {}", element);
            return true;
        }
        return false;
    }

    private void scanComponent(TypeElement typeElement) {
        var model = modelUtils.createComponentModel(typeElement);
        var componentAnnotation = typeElement.getAnnotationsByType(Component.class);
        var applicationAnnotation = typeElement.getAnnotationsByType(Application.class);
        if (componentAnnotation.length == 0 && applicationAnnotation.length == 0) {
            log.debug("PROCESS: Adding component to staging: {}", typeElement);
            stagedComponents.addComponent(model);
        } else {
            log.debug("PROCESS: Adding component to index: {}", typeElement);
            componentMap.addComponent(model);
        }
        if (applicationAnnotation.length > 0) {
            this.appMainComponent = model;
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
        try (var out = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", "META-INF/components.yml").openOutputStream();
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
            log.debug("Linking {} providers", stagedProviders.size());
        }
        while (!stagedProviders.isEmpty()) {
            log.info("{} providers yet to link", stagedProviders.size());
            var providerElement = stagedProviders.remove();
            log.info("Link provider {}", providerElement);
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
                log.error(msg);
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
                log.debug("Provider {} provides non existing component of {}. Creating ComponentModel", providerModel.getInstantiable(), key);
                componentModel = modelUtils.createComponentModel(componentType, providerType);
                componentModel.setQualifiers(qualifiers);
                componentMap.addComponent(componentModel);
            }
            log.info("Assigning provider {} for component {}", providerElement, componentType);
            componentModel.setProvider(providerModel);
        }
    }

    private void resolveAndGenerateProviders() throws IOException {
        var toAdd = new ArrayList<ComponentModel>();
        var toGenerate = new ArrayList<ComponentModel>();
        componentMap.allComponentsStream()
                .forEach(cm -> {
                    var provider = cm.getProvider();
                    if (provider == null) {
                        log.info("Component {} doesn't have a provider yet. Will be generated.", cm.getInstantiable());
                        toGenerate.add(cm);
                    } else {
                        log.info("Resolving dependencies of provider {} (component {})", provider.getInstantiable(), cm.getInstantiable());
                        for (var d : provider.getDependencies()) {
                            log.debug("resolving {} of type {}}", d.getName(), d.getType());
                            var k = new ComponentKey(d.getType(), d.getQualifiers());
                            var components = stagedComponents.findAndRemoveComponentModels(k);
                            toAdd.addAll(components);
                        }
                    }
                });
        componentMap.addComponents(toAdd);
        toGenerate.addAll(toAdd);
        for (var c : toGenerate) {
            log.info("Generating provider for component {}.", c.getInstantiable());
            generateProvider(c);
        }
    }

    private void generateProvider(ComponentModel model) throws IOException {
        TypeElement componentElement = processingEnv.getElementUtils().getTypeElement(model.getInstantiable());
        List<DependencyModel> dependencies = modelUtils.collectConstructorParams(componentElement);
        defaultProviderGenerator.generate(model, componentElement, dependencies);
    }


    private abstract class ProcessingContextImpl implements ProcessingContext {
        private final ComponentManager componentManager = new ComponentManagerImpl();

        @Override
        public ProcessingEnvironment processingEnv() {
            return processingEnv;
        }

        @Override
        public ComponentManager componentManager() {
            return componentManager;
        }

    }

    private class ComponentManagerImpl implements ComponentManager {

        @Override
        public boolean useComponent(TypeMirror type, QualifierModel qualifier) {
            return !useAndGetComponents(type, qualifier).isEmpty();
        }

        @Override
        public Collection<ComponentModel> useAndGetComponents(TypeMirror type, QualifierModel qualifier) {
            var key = new ComponentKey(type.toString(), qualifier);
            var comps = stagedComponents.findAndRemoveComponentModels(key);
            componentMap.addComponents(comps);
            return componentMap.findComponentModels(key);
        }

        @Override
        public QualifierModel extractQualifier(AnnotatedConstruct annotated) {
            return modelUtils.extractQualifiers(annotated);
        }
    }
}
