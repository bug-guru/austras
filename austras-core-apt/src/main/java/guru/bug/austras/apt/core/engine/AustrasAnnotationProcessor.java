package guru.bug.austras.apt.core.engine;

import guru.bug.austras.apt.core.ComponentMap;
import guru.bug.austras.apt.core.ModelUtils;
import guru.bug.austras.apt.core.UniqueNameGenerator;
import guru.bug.austras.apt.core.model.ComponentKey;
import guru.bug.austras.apt.core.model.ComponentModel;
import guru.bug.austras.apt.core.model.QualifierModel;
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
import javax.lang.model.type.TypeMirror;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

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
    private ComponentMap componentMap;
    private ComponentModel appMainComponent;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        log.debug("initializing AustrasAnnotationProcessor");
        super.init(processingEnv);
        this.modelUtils = new ModelUtils(uniqueNameGenerator, processingEnv);

        this.componentMap = new ComponentMap();
        readComponentMaps();
        initPlugins();
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
                        componentMap.importComponent(comp);
                    }
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException(e); // TODO
        }
    }

    private void initPlugins() {
        var loader = ServiceLoader.load(AustrasProcessorPlugin.class, this.getClass().getClassLoader());
        this.plugins = loader.stream().map(ServiceLoader.Provider::get).collect(Collectors.toList());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        var ctx = new ProcessingContextImpl() {

            @Override
            public RoundEnvironment roundEnv() {
                return roundEnv;
            }
        };
        try {
            if (roundEnv.processingOver()) {
                generateComponentMap();
                generateAppMain(ctx);
            } else {
                log.debug("SCAN");
                var rootElements = roundEnv.getRootElements();
                scanRootElements(rootElements);
                log.debug("PLUGINS");
                processPlugins(ctx);
            }
            return false;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private void generateAppMain(ProcessingContext ctx) {
        if (this.appMainComponent == null) {
            log.info("No main class defined");
            return;
        }
        try {
            var mainClassGenerator = new MainClassGenerator(ctx);

        } catch (IOException | TemplateException e) {
            throw new IllegalStateException(e);
        }
    }

    private void scanRootElements(Set<? extends Element> rootElements) {
        for (var element : rootElements) {
            if (shouldBeIgnored(element)) {
                continue;
            }
            TypeElement typeElement = (TypeElement) element;
            log.info("PROCESS: Found component class {}.", element);
            scanComponent(typeElement);
        }
    }

    private void processPlugins(ProcessingContext ctx) {
        for (var p : plugins) {
            log.debug("Processing {}", p.getClass().getName());
            p.process(ctx);
        }
    }

    private void scanComponent(TypeElement typeElement) {
        var model = modelUtils.createComponentModel(typeElement);
        var hasComponentAnnotation = typeElement.getAnnotationsByType(Component.class).length > 0;
        var hasApplicationAnnotation = typeElement.getAnnotationsByType(Application.class).length > 0;
        if (hasComponentAnnotation || hasApplicationAnnotation) {
            log.debug("PROCESS: Adding component to index: {}", typeElement);
            componentMap.publishComponent(model);
        } else {
            log.debug("PROCESS: Adding component to staging: {}", typeElement);
            componentMap.stageComponent(model);
        }
        if (hasApplicationAnnotation) {
            this.appMainComponent = model;
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

        @Override
        public ModelUtils modelUtils() {
            return modelUtils;
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
            return componentMap.findComponentModels(key);
        }

        @Override
        public QualifierModel extractQualifier(AnnotatedConstruct annotated) {
            return modelUtils.extractQualifiers(annotated);
        }
    }
}
