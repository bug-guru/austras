package guru.bug.fuzzbagel.apt.core;

import guru.bug.fuzzbagel.apt.core.componentmap.ComponentMap;
import guru.bug.fuzzbagel.privider.ComponentProvider;
import guru.bug.fuzzbagel.privider.GlobalComponentProvider;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import static javax.lang.model.element.ElementKind.CLASS;
import static javax.lang.model.element.ElementKind.INTERFACE;

@SupportedAnnotationTypes("*" )
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class AnnotationProcessorCore extends AbstractFuzzBagelAnnotationProcessor {
    private ComponentMap componentMap;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        var providerType = processingEnv.getElementUtils().getTypeElement(ComponentProvider.class.getName());
        componentMap = new ComponentMap(this, providerType);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            generateAppMain();
        } else {
            scanRootElements(roundEnv.getRootElements());
            generateProviders();
        }
        return false;
    }

    private void generateProviders() {
        componentMap.resolveProviders();
        componentMap.allComponentsStream()
                .filter(cd -> cd.getProviderType() == null)
                .forEach(cd -> generateProvider(cd.getComponentType()));
    }

    private void generateProvider(TypeElement componentType) {
        var pkgName = processingEnv.getElementUtils().getPackageOf(componentType).getQualifiedName().toString();
        var compSimpleName = componentType.getSimpleName().toString();
        var provSimpleName = componentType.getSimpleName().toString().concat("Provider" );
        var provQualifiedName = pkgName.concat("." ).concat(provSimpleName);

        try {
            JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(provQualifiedName);
            try (var oos = sourceFile.openOutputStream();
                 var out = new PrintWriter(oos)) {
                out.printf("package %s;\n", pkgName);
                out.printf("public class %s extends %s<%s> implements %s<%s> {\n",
                        provSimpleName,
                        GlobalComponentProvider.class.getName(),
                        componentType.getQualifiedName(),
                        ComponentProvider.class.getName(),
                        componentType.getQualifiedName());

                out.printf("@Override\n" );
                out.printf("protected %s takeInstance() {\n", componentType.getQualifiedName());
                out.print("return null;\n" );
                out.print("}\n" );
                out.print("}" );
            }
        } catch (IOException e) {
            error("Unexpected error", e);
            throw new IllegalStateException(e);
        }
    }

    private void scanRootElements(Set<? extends Element> rootElements) {
        rootElements.forEach(this::scanElement);
    }

    private void scanElement(Element element) {
        if (element.getKind() == CLASS) {
            TypeElement type = (TypeElement) element;
            if (type.getModifiers().contains(Modifier.ABSTRACT)) {
                debug("IGNORE: Found abstract class %s", element);
            } else {
                debug("PROCESS: Found class %s.", element);
                componentMap.addClass(type);
            }
        } else if (element.getKind() == INTERFACE) {
            debug("IGNORE: Found interface %s.", element);
        } else {
            debug("IGNORE: Unknown element %s.", element);
        }
    }

    private void generateAppMain() {
        debug("Component Map: %s", componentMap);
    }
}
