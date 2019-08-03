package guru.bug.austras.apt.core;

import guru.bug.austras.apt.core.componentmap.ComponentMap;
import guru.bug.austras.apt.core.componentmap.ComponentKey;
import guru.bug.austras.apt.core.componentmap.UniqueNameGenerator;
import guru.bug.austras.provider.ComponentProvider;

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


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.elementUtils = processingEnv.getElementUtils();
        this.typeUtils = processingEnv.getTypeUtils();
        this.providerInterfaceType = typeUtils.getDeclaredType(elementUtils.getTypeElement(ComponentProvider.class.getName()));
        this.modelUtils = new ModelUtils(this, uniqueNameGenerator, typeUtils, elementUtils, providerInterfaceType);
        this.componentMap = new ComponentMap(this);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            generateComponentMap();
            generateAppMain();
        } else {
            scanRootElements(roundEnv.getRootElements());
            resolveProviders();
//            generateProviders();
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
            var qualifier = modelUtils.extractQualifier(providerElement);
            var key = new ComponentKey(type.toString(), qualifier);
            var componentModel = componentMap.findSingleComponentModel(key);
            debug("Setting provider %s for component %s", providerElement, componentModel);
            if (componentModel.getProvider() != null) {
                throw new IllegalStateException("Provider already set" );
            }
//            if (foundSet == null || foundSet.size() != 1) {
//                throw new IllegalStateException("expected only one component" ); // TODO better error handling
//            }
//            var cd = (ComponentDescription) foundSet.stream().findFirst().orElseThrow();
//            log.debug("Setting provider %s for %s", providerElement.getType(), cd.getType());
//            cd.setProvider(providerElement);
        }
    }
//
//    private void generateProviders() {
//        componentMap.resolveProviders();
//        System.out.printf("Components: %s\n", componentMap);
//        var codeGenerator = new StandardProviderCodeGenerator(processingEnv);
//        componentMap.allComponentsStream()
//                .filter(cd -> cd instanceof ComponentDescription)
//                .map(cd -> cd)
//                .filter(cd -> cd.getProvider() == null)
//                .forEach(codeGenerator::generateProvider);
//    }

    private void generateAppMain() {
        debug("Component Map: %s", componentMap);
    }
}
