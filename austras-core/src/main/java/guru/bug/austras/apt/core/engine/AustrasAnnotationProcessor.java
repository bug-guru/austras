/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.apt.core.engine;

import guru.bug.austras.apt.core.ComponentMap;
import guru.bug.austras.apt.core.ModelUtils;
import guru.bug.austras.apt.core.UniqueNameGenerator;
import guru.bug.austras.apt.core.common.model.*;
import guru.bug.austras.apt.core.process.MainClassGenerator;
import guru.bug.austras.apt.core.process.ModuleModelSerializer;
import guru.bug.austras.core.Application;
import guru.bug.austras.core.qualifiers.Qualifier;
import guru.bug.austras.core.qualifiers.Qualifiers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.*;
import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.StandardLocation;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

import static javax.lang.model.element.ElementKind.CLASS;
import static javax.lang.model.element.ElementKind.CONSTRUCTOR;
import static javax.lang.model.element.Modifier.PUBLIC;

@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class AustrasAnnotationProcessor extends AbstractProcessor {
    public static final String COMPONENTS_INDEX_FILE_NAME = "META-INF/components.json";
    public static final String PROCESSOR_PLUGIN_SERVICE_DIR_NAME = "META-INF/services/";
    private static final Logger log = LoggerFactory.getLogger(AustrasAnnotationProcessor.class);
    private final UniqueNameGenerator uniqueNameGenerator = new UniqueNameGenerator();
    private final Set<AnnotationMirror> registeredQualifiers = new HashSet<>();
    private List<AustrasProcessorPlugin> plugins;
    private ModelUtils modelUtils;
    private ComponentMap componentMap;
    private ComponentModel appMainComponent;
    private List<String> foundProcessorPlugins = new ArrayList<>();

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
            var allMaps = classLoader.getResources(COMPONENTS_INDEX_FILE_NAME);
            while (allMaps.hasMoreElements()) {
                var map = allMaps.nextElement();
                log.info("Loading components from {}", map);
                try (var stream = map.openStream();
                     var bufStream = new BufferedInputStream(stream);
                     var reader = new InputStreamReader(bufStream)) {
                    var moduleModel = ModuleModelSerializer.load(reader);
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
        if (roundEnv.errorRaised()) {
            log.error("Error was raised");
            return false;
        }
        var ctx = new ProcessingContextImpl(roundEnv);
        try {
            if (roundEnv.processingOver()) {
                generatePluginServices();
                generateAppMain(ctx);
                generateComponentMap();
            } else {
                log.debug("SCAN");
                var rootElements = roundEnv.getRootElements();
                Set<ComponentRef> dependencies = scanRootElements(rootElements);
                ctx.setRoundDependencies(dependencies);
                log.debug("PLUGINS");
                processPlugins(ctx);
            }
            return false;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private void generatePluginServices() {
        if (foundProcessorPlugins.isEmpty()) {
            return;
        }
        try (var out = processingEnv.getFiler().createResource(
                StandardLocation.CLASS_OUTPUT,
                "",
                PROCESSOR_PLUGIN_SERVICE_DIR_NAME + AustrasProcessorPlugin.class.getName()
        ).openWriter()) {
            for (var l : foundProcessorPlugins) {
                out.write(l);
                out.write('\n');
            }
        } catch (IOException e) {
            log.error("Creation of services file is failed", e);
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Creation of services file is failed");
        }
    }

    private void generateAppMain(ProcessingContext ctx) {
        if (this.appMainComponent == null) {
            log.info("No main class defined");
            return;
        }
        var mainClassGenerator = new MainClassGenerator(ctx);
        mainClassGenerator.generateAppMain(this.appMainComponent);
    }

    private Set<ComponentRef> scanRootElements(Set<? extends Element> rootElements) {
        var dependencies = new HashSet<ComponentRef>();
        for (var element : rootElements) {
            if (isProcessorPlugin(element)) {
                var procType = (TypeElement) element;
                foundProcessorPlugins.add(procType.getQualifiedName().toString());
            } else if (!shouldBeIgnored(element)) {
                TypeElement typeElement = (TypeElement) element;
                log.info("PROCESS: Found component class {}.", element);
                var refs = scanComponent(typeElement).stream()
                        .flatMap(d -> d.getDependencies().stream())
                        .map(DependencyModel::asComponentRef)
                        .collect(Collectors.toSet());
                dependencies.addAll(refs);
            }
        }
        return dependencies;
    }

    private boolean isProcessorPlugin(Element element) {
        if (element.getKind() != CLASS) {
            return false;
        }
        var procTypeElement = (TypeElement) element;
        var procDeclType = (DeclaredType) procTypeElement.asType();
        return modelUtils.collectAllAncestor(procDeclType).stream()
                .anyMatch(dt -> dt.toString().equals(AustrasProcessorPlugin.class.getName()));
    }

    private void processPlugins(ProcessingContext ctx) {
        for (var p : plugins) {
            log.debug("Processing {}", p.getClass().getName());
            p.process(ctx);
        }
    }

    private Optional<ComponentModel> scanComponent(TypeElement typeElement) {
        try {
            var model = modelUtils.createComponentModel(typeElement);
            var hasQualifierAnnotation = hasQualifiers(typeElement);
            var hasApplicationAnnotation = typeElement.getAnnotationsByType(Application.class).length > 0;
            if (hasQualifierAnnotation || hasApplicationAnnotation) {
                log.debug("PROCESS: Adding component to index: {}", typeElement);
                componentMap.publishComponent(model);
            } else {
                log.debug("PROCESS: Adding component to staging: {}", typeElement);
                componentMap.stageComponent(model);
            }
            if (hasApplicationAnnotation) {
                this.appMainComponent = model;
            }
            return Optional.of(model);
        } catch (Exception e) {
            log.error("Exception while scanning component", e);
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Exception while scanning component", typeElement);
            return Optional.empty();
        }
    }

    private boolean hasQualifiers(TypeElement typeElement) {
        var qualifierClassName = Qualifier.class.getName();
        var qualifiersClassName = Qualifiers.class.getName();
        var checked = new HashSet<AnnotationMirror>();
        var toCheck = new ArrayDeque<AnnotationMirror>(typeElement.getAnnotationMirrors());
        while (!toCheck.isEmpty()) {
            var ae = toCheck.remove();
            if (registeredQualifiers.contains(ae)) {
                return true;
            }
            if (!checked.add(ae)) {
                continue;
            }
            var annotationType = (TypeElement) ae.getAnnotationType().asElement();
            var annotationClassName = annotationType.getQualifiedName();
            if (annotationClassName.contentEquals(qualifierClassName) || annotationClassName.contentEquals(qualifiersClassName)) {
                registeredQualifiers.add(ae);
                return true;
            }
            toCheck.addAll(annotationType.getAnnotationMirrors());
        }
        return false;
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
        try (var out = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", COMPONENTS_INDEX_FILE_NAME).openOutputStream();
             var w = new PrintWriter(out)) {
            componentMap.serialize(w);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private class ProcessingContextImpl implements ProcessingContext {
        private final RoundEnvironment roundEnv;
        private Set<ComponentRef> roundDependencies = Set.of();
        private final ComponentManagerImpl componentManager = new ComponentManagerImpl() {
            @Override
            public Set<ComponentRef> roundDependencies() {
                return roundDependencies;
            }
        };

        private ProcessingContextImpl(RoundEnvironment roundEnv) {
            this.roundEnv = roundEnv;
        }

        @Override
        public ProcessingEnvironment processingEnv() {
            return processingEnv;
        }

        @Override
        public RoundEnvironment roundEnv() {
            return roundEnv;
        }

        @Override
        public ComponentManager componentManager() {
            return componentManager;
        }

        @Override
        public ModelUtils modelUtils() {
            return modelUtils;
        }

        public void setRoundDependencies(Set<ComponentRef> roundDependencies) {
            this.roundDependencies = Set.copyOf(roundDependencies);
        }
    }

    private abstract class ComponentManagerImpl implements ComponentManager {

        @Override
        public boolean tryUseComponents(TypeMirror type, QualifierSetModel qualifier) {
            return tryUseComponents(type.toString(), qualifier);
        }

        @Override
        public Collection<ComponentModel> findComponents(TypeMirror type, QualifierSetModel qualifier) {
            return findComponents(type.toString(), qualifier);
        }

        @Override
        public Optional<ComponentModel> findSingleComponent(TypeMirror type, QualifierSetModel qualifier) {
            return findSingleComponent(type.toString(), qualifier);
        }

        @Override
        public boolean tryUseComponents(Class<?> type, QualifierSetModel qualifier) {
            return tryUseComponents(type.getName(), qualifier);
        }

        @Override
        public Collection<ComponentModel> findComponents(Class<?> type, QualifierSetModel qualifier) {
            return findComponents(type.getName(), qualifier);
        }

        @Override
        public Optional<ComponentModel> findSingleComponent(Class<?> type, QualifierSetModel qualifier) {
            return findSingleComponent(type.getName(), qualifier);
        }

        @Override
        public boolean tryUseComponents(String type, QualifierSetModel qualifier) {
            var key = ComponentKey.of(type, qualifier);
            return tryUseComponents(key);
        }

        @Override
        public Collection<ComponentModel> findComponents(String type, QualifierSetModel qualifier) {
            var key = ComponentKey.of(type, qualifier);
            return findComponents(key);
        }

        @Override
        public Optional<ComponentModel> findSingleComponent(String type, QualifierSetModel qualifier) {
            var key = ComponentKey.of(type, qualifier);
            return findSingleComponent(key);
        }

        @Override
        public boolean tryUseComponents(DependencyModel dependencyModel) {
            return tryUseComponents(dependencyModel.getType(), dependencyModel.getQualifiers());
        }

        @Override
        public Collection<ComponentModel> findComponents(DependencyModel dependencyModel) {
            return findComponents(dependencyModel.getType(), dependencyModel.getQualifiers());
        }

        @Override
        public Optional<ComponentModel> findSingleComponent(DependencyModel dependencyModel) {
            return findSingleComponent(dependencyModel.getType(), dependencyModel.getQualifiers());
        }

        @Override
        public boolean tryUseComponents(ComponentKey key) {
            return componentMap.tryUseComponentModels(key);
        }

        @Override
        public Collection<ComponentModel> findComponents(ComponentKey key) {
            return componentMap.findComponentModels(key);
        }

        @Override
        public Optional<ComponentModel> findSingleComponent(ComponentKey key) {
            return componentMap.findSingleComponentModel(key);
        }

        @Override
        public QualifierSetModel extractQualifier(AnnotatedConstruct annotated) {
            return modelUtils.extractQualifiers(annotated);
        }

    }
}
