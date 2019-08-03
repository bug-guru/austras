package guru.bug.fuzzbagel.apt.core.generators;

import guru.bug.fuzzbagel.annotations.Qualifier;
import guru.bug.fuzzbagel.apt.core.componentmap.ComponentDescription;
import guru.bug.fuzzbagel.provider.ComponentProvider;
import guru.bug.fuzzbagel.provider.GlobalComponentProvider;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StandardProviderCodeGenerator {
    private final ProcessingEnvironment processingEnv;

    public StandardProviderCodeGenerator(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }

    public void generateProvider(ComponentDescription cd) {
        var ct = cd.getType();
        var pkgName = processingEnv.getElementUtils().getPackageOf(ct).getQualifiedName().toString();
        var provSimpleName = ct.getSimpleName() + "Provider";
        var provQualifiedName = pkgName + "." + provSimpleName;
        try {
            JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(provQualifiedName);
            try (var oos = sourceFile.openOutputStream();
                 var out = new PrintWriter(oos)) {
                out.printf("package %s;\n", pkgName);
                Qualifier qualifier = cd.getType().getAnnotation(Qualifier.class);
                if (qualifier != null) {
                    out.printf("@%s(\"%s\")", Qualifier.class.getName(), qualifier.value());
                }
                out.printf("public class %s extends %s<%s> implements %s<%s> {\n",
                        provSimpleName,
                        GlobalComponentProvider.class.getName(),
                        ct.getQualifiedName(),
                        ComponentProvider.class.getName(),
                        ct.getQualifiedName());

                generateProviderFields(out, ct);

                generateProviderConstructor(out, provSimpleName, ct);

                generateProviderTakeInstance(out, ct);

                out.print("}" );
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void generateProviderTakeInstance(PrintWriter out, TypeElement componentTypeElement) {
        out.printf("\t@Override\n" );
        out.printf("\tprotected %s takeInstance() {\n", componentTypeElement.getQualifiedName());
        streamOfConstructorParameters(componentTypeElement)
                .forEach(p -> out.printf("\t\tvar %s = this.%s.get();\n",
                        p.getSimpleName(),
                        p.getSimpleName() + "Provider" ));
        out.printf("\t\treturn new %s(%s);\n",
                componentTypeElement.getQualifiedName().toString(),
                streamOfConstructorParameters(componentTypeElement)
                        .map(VariableElement::getSimpleName)
                        .collect(Collectors.joining("," ))
        );
        out.print("\t}\n" );
    }

    private void generateProviderFields(PrintWriter out, TypeElement componentTypeElement) {
        streamOfConstructorParameters(componentTypeElement)
                .forEach(p -> {
                    DeclaredType paramDeclType = (DeclaredType) p.asType();
                    out.printf("\tprivate final %s<%s> %s;\n",
                            ComponentProvider.class.getName(),
                            paramDeclType.toString(),
                            p.getSimpleName() + "Provider" );
                });
    }

    private void generateProviderConstructor(PrintWriter out, String simpleClassName, TypeElement componentTypeElement) {
        out.printf("\tpublic %s(", simpleClassName);
        out.print(streamOfConstructorParameters(componentTypeElement).map(p -> {
            var paramType = p.asType();
            if (paramType.getKind() == TypeKind.DECLARED) {
                DeclaredType paramDeclType = (DeclaredType) paramType;
                return String.format("%s<%s> %s",
                        ComponentProvider.class.getName(),
                        paramDeclType.toString(),
                        p.getSimpleName() + "Provider" );
            } else {
                throw new UnsupportedOperationException();
            }
        }).collect(Collectors.joining("," )));
        out.print(") {\n" );
        streamOfConstructorParameters(componentTypeElement)
                .forEach(p -> {
                    DeclaredType paramDeclType = (DeclaredType) p.asType();
                    String varName = p.getSimpleName() + "Provider";
                    out.printf("\t\tthis.%s = %s;\n", varName, varName);
                });
        out.print("\t}\n" );
    }

    private Stream<? extends VariableElement> streamOfConstructorParameters(TypeElement componentType) {
        return componentType.getEnclosedElements().stream()
                .filter(m -> m.getKind() == ElementKind.CONSTRUCTOR)
                .filter(m -> m.getModifiers().contains(Modifier.PUBLIC))
                .findFirst()
                .map(c -> (ExecutableElement) c)
                .map(c -> c.getParameters().stream())
                .orElse(Stream.empty());
    }

}
