package guru.bug.austras.apt.core.generators;

import guru.bug.austras.annotations.Qualifier;
import guru.bug.austras.annotations.QualifierProperty;
import guru.bug.austras.apt.model.ComponentModel;
import guru.bug.austras.apt.model.DependencyModel;
import guru.bug.austras.apt.model.QualifierModel;
import guru.bug.austras.provider.Provider;
import org.apache.commons.text.StringEscapeUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

public class NonCachingProviderGenerator implements ProviderGenerator {
    private final ProcessingEnvironment processingEnv;
    private final ComponentModel componentModel;
    private final List<DependencyModel> dependencies;

    public NonCachingProviderGenerator(ProcessingEnvironment processingEnv, ComponentModel componentModel, List<DependencyModel> dependencies) {

        this.processingEnv = processingEnv;
        this.componentModel = componentModel;
        this.dependencies = dependencies;
    }

    private String extractPackageName(String qualifiedName) {
        var lastDotIdx = qualifiedName.lastIndexOf('.');
        return qualifiedName.substring(0, lastDotIdx);
    }

    private String extractSimpleName(String qualifiedName) {
        var lastDotIdx = qualifiedName.lastIndexOf('.');
        return qualifiedName.substring(lastDotIdx + 1);
    }

    @Override
    public void generateProvider() {
        var compQName = componentModel.getInstantiable();
        var provQName = compQName + "Provider";
        var provPkgName = extractPackageName(compQName);
        var provSimpleName = extractSimpleName(compQName);
        try {
            JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(provQName);
            try (var oos = sourceFile.openOutputStream();
                 var out = new PrintWriter(oos)) {
                out.printf("package %s;\n", provPkgName);
                generateQualifierAnnotations(componentModel.getQualifiers(), out);
                out.printf("public class %s implements %s<%s> {\n",
                        provSimpleName,
                        Provider.class.getName(),
                        compQName);

                generateProviderFields(out);
                generateProviderConstructor(out, provSimpleName);
                generateGetInstance(out);

                out.print("}" );
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void generateQualifierAnnotations(List<QualifierModel> qualifierList, PrintWriter out) {
        if (qualifierList == null || qualifierList.isEmpty()) {
            return;
        }
        for (var q : qualifierList) {
            var props = q.getProperties().entrySet().stream()
                    .map(e -> String.format("@%s(name=\"%s\",value=\"%s\")",
                            QualifierProperty.class.getName(),
                            StringEscapeUtils.escapeJava(e.getKey()),
                            StringEscapeUtils.escapeJava(e.getValue())))
                    .collect(Collectors.joining(",", "{", "}" ));

            out.printf("@%s(name=\"%s\", properties=%s)\n", Qualifier.class.getName(), q.getName(), props);
        }
    }

    private void generateProviderFields(PrintWriter out) {
        out.printf("\tprivate final %s %s;\n\n", componentModel.getInstantiable(), componentModel.getName());
        dependencies.forEach(p -> {
            out.printf("\tprivate final %s<%s> %s;\n",
                    Provider.class.getName(),
                    p.getType(),
                    p.getName() + "Provider" );
        });
    }

    private void generateProviderConstructor(PrintWriter out, String simpleName) {
        out.printf("\tpublic %s(", simpleName);
        String params = dependencies.stream()
                .map(p -> String.format("%s<%s> %s",
                        Provider.class.getName(),
                        p.getType(),
                        p.getName() + "Provider" ))
                .collect(Collectors.joining("," ));
        out.print(params);
        out.print(") {\n" );
        dependencies.forEach(p -> {
            String varName = p.getName() + "Provider";
            out.printf("\t\tthis.%s = %s;\n", varName, varName);
        });
        out.print("\t}\n" );
    }

    private void generateGetInstance(PrintWriter out) {
        out.printf("\t@Override\n" );
        out.printf("\tprotected %s get() {\n", componentModel.getInstantiable());
        out.printf("\t\treturn this.%s;\n", componentModel.getName());
        out.print("\t}\n\n" );
    }

}
