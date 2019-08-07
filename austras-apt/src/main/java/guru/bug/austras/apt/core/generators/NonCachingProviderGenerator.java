package guru.bug.austras.apt.core.generators;

import guru.bug.austras.annotations.Qualifier;
import guru.bug.austras.annotations.QualifierProperty;
import guru.bug.austras.apt.model.ComponentModel;
import guru.bug.austras.apt.model.QualifierModel;
import guru.bug.austras.provider.Provider;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

public class NonCachingProviderGenerator implements ProviderGenerator {
    private ProcessingEnvironment processingEnv;

    public NonCachingProviderGenerator(ProcessingEnvironment processingEnv) {

        this.processingEnv = processingEnv;
    }

    private String extractPackageName(String qualifiedName) {
        var lastDotIdx = qualifiedName.lastIndexOf('.');
        return qualifiedName.substring(0, lastDotIdx);
    }

    private String extractSimpleName(String qualifiedName) {
        var lastDotIdx = qualifiedName.lastIndexOf('.');
        return qualifiedName.substring(lastDotIdx + 1);
    }

    public void generateProvider(ComponentModel cd) {
        var compQName = cd.getInstantiable();
        var provQName = compQName + "Provider";
        var provPkgName = extractPackageName(compQName);
        var provSimpleName = extractSimpleName(compQName);
        try {
            JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(provQName);
            try (var oos = sourceFile.openOutputStream();
                 var out = new PrintWriter(oos)) {
                out.printf("package %s;\n", provPkgName);
                generateQualifierAnnotations(cd.getQualifiers(), out);
                out.printf("public class %s implements %s<%s> {\n",
                        provSimpleName,
                        Provider.class.getName(),
                        compQName);

                generateProviderFields(out, cd);
                generateProviderConstructor(out, provSimpleName, cd);
                generateGetInstance(out, cd);

                out.print("}");
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
                    .map(e -> String.format("@%s(name=\"%s\",name=\"%s\")",
                            QualifierProperty.class.getName(),
                            e.getKey(),
                            e.getValue()))
                    .collect(Collectors.joining(",", "{", "}"));

            out.printf("@%s(name=\"%s\", properties=%s)\n", Qualifier.class.getName(), q.getName(), props);
        }
    }

    private void generateProviderFields(PrintWriter out, ComponentModel cm) {
        var dependencies = cm.getDependencies();
        out.printf("\tprivate final %s %s;\n\n", cm.getInstantiable(), cm.getName());
        dependencies.forEach(p -> {
            out.printf("\tprivate final %s<%s> %s;\n",
                    Provider.class.getName(),
                    p.getType(),
                    p.getName() + "Provider");
        });
    }

    private void generateProviderConstructor(PrintWriter out, String simpleName, ComponentModel cm) {
        out.printf("\tpublic %s(", simpleName);
        String params = cm.getDependencies().stream()
                .map(p -> String.format("%s<%s> %s",
                        Provider.class.getName(),
                        p.getType(),
                        p.getName() + "Provider"))
                .collect(Collectors.joining(","));
        out.print(params);
        out.print(") {\n");
        cm.getDependencies().forEach(p -> {
            String varName = p.getName() + "Provider";
            out.printf("\t\tthis.%s = %s;\n", varName, varName);
        });
        out.print("\t}\n");
    }

    private void generateGetInstance(PrintWriter out, ComponentModel cm) {
        out.printf("\t@Override\n");
        out.printf("\tprotected %s get() {\n", cm.getInstantiable());
        out.printf("\t\treturn this.%s;\n", cm.getName());
        out.print("\t}\n\n");
    }

}
