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

public class EagerSingletonProviderGenerator implements ProviderGenerator {
    private final ProcessingEnvironment processingEnv;
    private final ComponentModel componentModel;
    private final List<DependencyModel> dependencies;

    public EagerSingletonProviderGenerator(ProcessingEnvironment processingEnv, ComponentModel componentModel, List<DependencyModel> dependencies) {
        this.processingEnv = processingEnv;
        this.componentModel = componentModel;
        this.dependencies = dependencies;
    }

    public void generateProvider() {
        var compQName = componentModel.getInstantiable();
        var provQName = compQName + "Provider";
        var provPkgName = extractPackageName(compQName);
        var compSimpleName = extractSimpleName(compQName);
        var provSimpleName = extractSimpleName(provQName);
        try {
            JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(provQName);
            try (var oos = sourceFile.openOutputStream();
                 var out = new PrintWriter(oos)) {
                out.printf("package %s;\n", provPkgName);
                var qanno = generateQualifierAnnotations(componentModel.getQualifiers(), true);
                out.print(qanno);
                out.printf("public class %s implements %s<%s> {\n",
                        provSimpleName,
                        Provider.class.getName(),
                        compQName);

                generateProviderFields(out);
                generateProviderConstructor(out, provSimpleName, compSimpleName);
                generateGetInstance(out);

                out.print("}" );
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private String extractPackageName(String qualifiedName) {
        var lastDotIdx = qualifiedName.lastIndexOf('.');
        return qualifiedName.substring(0, lastDotIdx);
    }

    private String extractSimpleName(String qualifiedName) {
        var lastDotIdx = qualifiedName.lastIndexOf('.');
        return qualifiedName.substring(lastDotIdx + 1);
    }

    private String generateQualifierAnnotations(List<QualifierModel> qualifierList, boolean multiline) {
        if (qualifierList == null || qualifierList.isEmpty()) {
            return "";
        }
        var result = new StringBuilder(256);
        for (var q : qualifierList) {
            var props = q.getProperties().entrySet().stream()
                    .map(e -> String.format("@%s(name=\"%s\",value=\"%s\")",
                            QualifierProperty.class.getName(),
                            StringEscapeUtils.escapeJava(e.getKey()),
                            StringEscapeUtils.escapeJava(e.getValue())))
                    .collect(Collectors.joining(",", "{", "}" ));
            var qline = String.format("@%s(name=\"%s\", properties=%s)", Qualifier.class.getName(), q.getName(), props);
            result.append(qline);
            if (multiline) {
                result.append('\n');
            } else {
                result.append(' ');
            }
        }
        return result.toString();
    }

    private void generateProviderFields(PrintWriter out) {
        out.printf("\tprivate final %s %s;\n\n", componentModel.getInstantiable(), componentModel.getName());
    }

    private void generateProviderConstructor(PrintWriter out, String provSimpleName, String compSimpleName) {
        out.printf("\tpublic %s(", provSimpleName);
        String params = dependencies.stream()
                .map(p -> String.format("%s%s<%s> %s",
                        generateQualifierAnnotations(p.getQualifiers(), false),
                        Provider.class.getName(),
                        p.getType(),
                        p.getName() + "Provider" ))
                .collect(Collectors.joining("," ));
        out.print(params);
        out.print(") {\n" );
        dependencies.forEach(p -> {
            String compVarName = p.getName();
            String provVarName = compVarName + "Provider";
            out.printf("\t\tvar %s = %s.get();\n", compVarName, provVarName);
        });
        out.printf("\t\tthis.%s = new %s(", componentModel.getName(), compSimpleName);
        String vars = dependencies.stream()
                .map(DependencyModel::getName)
                .collect(Collectors.joining("," ));
        out.print(vars);
        out.print(");\n" );
        out.print("\t}\n" );
    }

    private void generateGetInstance(PrintWriter out) {
        out.printf("\t@Override\n" );
        out.printf("\tpublic %s get() {\n", componentModel.getInstantiable());
        out.printf("\t\treturn this.%s;\n", componentModel.getName());
        out.print("\t}\n\n" );
    }
}
