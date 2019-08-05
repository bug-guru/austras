package guru.bug.austras.apt.core;

import guru.bug.austras.apt.core.componentmap.ComponentKey;
import guru.bug.austras.apt.core.componentmap.ComponentMap;
import guru.bug.austras.apt.core.componentmap.UniqueNameGenerator;
import guru.bug.austras.apt.core.generators.CachingProviderGenerator;
import guru.bug.austras.apt.core.generators.EagerSingletonProviderGenerator;
import guru.bug.austras.apt.core.generators.NonCachingProviderGenerator;
import guru.bug.austras.apt.model.ProviderModel;
import guru.bug.austras.provider.Provider;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import static javax.lang.model.element.ElementKind.CLASS;
import static javax.lang.model.element.ElementKind.INTERFACE;

@SupportedAnnotationTypes("*" )
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class AnnotationProcessorCore extends AbstractAustrasAnnotationProcessor {
    private final Queue<TypeElement> providers = new LinkedList<>();
    private final UniqueNameGenerator uniqueNameGenerator = new UniqueNameGenerator();
    private ModelUtils modelUtils;
    private ComponentMap componentMap;
    private DeclaredType providerInterfaceType;
    private Elements elementUtils;
    private Types typeUtils;
    private EagerSingletonProviderGenerator eagerSingletonProviderCodeGenerator;
    private NonCachingProviderGenerator nonCachingProviderCodeGenerator;
    private CachingProviderGenerator cachingProviderCodeGenerator;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.elementUtils = processingEnv.getElementUtils();
        this.typeUtils = processingEnv.getTypeUtils();
        this.providerInterfaceType = typeUtils.getDeclaredType(elementUtils.getTypeElement(Provider.class.getName()));
        this.modelUtils = new ModelUtils(this, uniqueNameGenerator, typeUtils, elementUtils, providerInterfaceType);
        this.componentMap = new ComponentMap(this);
        this.eagerSingletonProviderCodeGenerator = new EagerSingletonProviderGenerator(processingEnv);
        this.nonCachingProviderCodeGenerator = new NonCachingProviderGenerator(processingEnv);
        this.cachingProviderCodeGenerator = new CachingProviderGenerator(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            generateComponentMap();
            generateAppMain();
        } else {
            scanRootElements(roundEnv.getRootElements());
            resolveProviders();
            generateProviders();
        }
        return false;
    }

    private void scanRootElements(Set<? extends Element> rootElements) {
        rootElements.forEach(this::scanElement);
    }

    private void scanElement(Element element) {
        if (element.getKind() == CLASS) {
            TypeElement type = (TypeElement) element;
            if (!checkIsProcessable(type)) {
                debug("IGNORE: Found abstract or non-public class %s", element);
            } else if (typeUtils.isAssignable(type.asType(), providerInterfaceType)) {
                debug("PROCESS: Found provider class %s.", element);
                providers.add(type);
            } else {
                debug("PROCESS: Found component class %s.", element);
                var model = modelUtils.createComponentModel(type);
                componentMap.addComponent(model);
            }
        } else if (element.getKind() == INTERFACE) {
            debug("IGNORE: Found interface %s.", element);
        } else {
            debug("IGNORE: Unknown element %s.", element);
        }
    }

    private boolean checkIsProcessable(TypeElement type) {
        var modifiers = type.getModifiers();
        return !modifiers.contains(Modifier.ABSTRACT) && modifiers.contains(Modifier.PUBLIC);
    }

    private void generateComponentMap() {
        try (var out = processingEnv.getFiler().createResource(StandardLocation.SOURCE_OUTPUT, "", "META-INF/components.yml" ).openOutputStream();
             var w = new PrintWriter(out)) {
            componentMap.serialize(w);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void resolveProviders() {
        debug("Resolving providers" );
        while (!providers.isEmpty()) {
            TypeElement providerElement = providers.remove();
            debug("Resolving provider %s", providerElement);
            var type = modelUtils.extractComponentTypeFromProvider(providerElement);
            var qualifiers = modelUtils.extractQualifiers(providerElement);
            var key = new ComponentKey(type.toString(), qualifiers);
            var componentModel = componentMap.findSingleComponentModel(key);
            debug("Setting provider %s for component %s", providerElement, componentModel);
            if (componentModel.getProvider() != null) {
                throw new IllegalStateException("Provider already set" );
            }
            var name = uniqueNameGenerator.findFreeVarName(providerElement);
            var dependencies = modelUtils.collectConstructorParams(providerElement);
            var instantiable = providerElement.toString();

            var providerModel = new ProviderModel();
            providerModel.setInstantiable(instantiable);
            providerModel.setName(name);
            providerModel.setDependencies(dependencies);

            componentModel.setProvider(providerModel);
        }
    }

    private void generateProviders() {
        componentMap.allComponentsStream()
                .filter(cm -> cm.getProvider() == null)
                .forEach(cm -> {
                    switch (cm.getCachingKind()) {
                        case EAGER_SINGLETON:
                            eagerSingletonProviderCodeGenerator.generateProvider(cm);
                            break;
                        case CACHED:
                            cachingProviderCodeGenerator.generateProvider(cm);
                            break;
                        case NO_CACHE:
                            nonCachingProviderCodeGenerator.generateProvider(cm);
                            break;
                        default:
                            throw new UnsupportedOperationException(cm.getCachingKind().toString());
                    }
                });
    }

    private void generateAppMain() {
        debug("Component Map: %s", componentMap);
    }
}
