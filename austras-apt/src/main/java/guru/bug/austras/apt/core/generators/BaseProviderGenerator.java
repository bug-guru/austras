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

public abstract class BaseProviderGenerator implements ProviderGenerator {

    protected final ProcessingEnvironment processingEnv;
    protected final ComponentModel componentModel;
    protected final List<DependencyModel> dependencies;
    protected final String componentQualifiedName;
    protected final String providerQualifiedName;
    protected final String providerPackageName;
    protected final String componentSimpleName;
    protected final String providerSimpleName;

    public BaseProviderGenerator(ProcessingEnvironment processingEnv, ComponentModel componentModel, List<DependencyModel> dependencies) {
        this.processingEnv = processingEnv;
        this.componentModel = componentModel;
        this.componentQualifiedName = componentModel.getInstantiable();
        this.dependencies = dependencies;
        this.providerQualifiedName = componentQualifiedName + "Provider";
        this.componentSimpleName = extractSimpleName(componentQualifiedName);
        this.providerPackageName = extractPackageName(providerQualifiedName);
        this.providerSimpleName = extractSimpleName(providerQualifiedName);
    }

    public void generateProvider() {
        try {
            JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(providerQualifiedName);
            try (var oos = sourceFile.openOutputStream();
                 var out = new PrintWriter(oos)) {
                out.printf("package %s;\n", providerPackageName);
                out.print(generateQualifierAnnotations(componentModel.getQualifiers(), true));
                out.printf("public class %s implements %s<%s> {\n",
                        providerSimpleName,
                        Provider.class.getName(),
                        componentQualifiedName);

                generateProviderFields(out);
                generateProviderConstructor(out);
                generateGetInstance(out);

                out.print("}" );
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    protected abstract void generateGetInstance(PrintWriter out);

    protected abstract void generateProviderConstructor(PrintWriter out);

    protected abstract void generateProviderFields(PrintWriter out);

    protected final String generateQualifierAnnotations(List<QualifierModel> qualifierList, boolean multiline) {
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

    private String extractPackageName(String qualifiedName) {
        var lastDotIdx = qualifiedName.lastIndexOf('.');
        return qualifiedName.substring(0, lastDotIdx);
    }

    private String extractSimpleName(String qualifiedName) {
        var lastDotIdx = qualifiedName.lastIndexOf('.');
        return qualifiedName.substring(lastDotIdx + 1);
    }
}
