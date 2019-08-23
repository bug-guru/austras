package guru.bug.austras.apt.core.generators;

import guru.bug.austras.apt.model.ComponentModel;
import guru.bug.austras.apt.model.DependencyModel;
import guru.bug.austras.apt.model.QualifierModel;
import guru.bug.austras.core.Qualifier;
import guru.bug.austras.core.QualifierProperty;
import guru.bug.austras.provider.Provider;
import org.apache.commons.text.StringEscapeUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public abstract class BaseProviderGenerator implements ProviderGenerator {

    protected final ProcessingEnvironment processingEnv;
    protected final ComponentModel componentModel;
    protected final List<Dependency> providerDependencies;
    protected final String componentQualifiedName;
    protected final String providerQualifiedName;
    protected final String providerPackageName;
    protected final String componentSimpleName;
    protected final String providerSimpleName;

    public BaseProviderGenerator(ProcessingEnvironment processingEnv, ComponentModel componentModel, List<DependencyModel> componentDependencies) {
        this.processingEnv = processingEnv;
        this.componentModel = componentModel;
        this.componentQualifiedName = componentModel.getInstantiable();
        this.providerQualifiedName = componentQualifiedName + "Provider";
        this.componentSimpleName = extractSimpleName(componentQualifiedName);
        this.providerPackageName = extractPackageName(providerQualifiedName);
        this.providerSimpleName = extractSimpleName(providerQualifiedName);
        this.providerDependencies = componentDependencies.stream()
                .map(Dependency::new)
                .collect(Collectors.toList());
    }

    public final void generateProvider() {
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
                out.print("}");
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void generateProviderFields(PrintWriter out) {
        out.print("\n");
        generateProviderFields((className, varName) -> out.printf("\tprivate final %s %s;\n", className, varName));
        out.print("\n");
    }

    protected void generateProviderFields(BiConsumer<String, String> fieldGenerator) {

    }

    private void generateGetInstance(PrintWriter out) {
        out.printf("\t@Override\n");
        out.printf("\tpublic %s get() {\n", componentModel.getInstantiable());
        generateGetMethodBody(out);
        out.print("\t}\n\n");

    }

    protected abstract void generateGetMethodBody(PrintWriter out);

    protected void generateConstructorParams(BiConsumer<String, String> paramGenerator) {
        providerDependencies.forEach(p -> {
            String type = String.format("%s%s",
                    generateQualifierAnnotations(p.providerDependency.getQualifiers(), false),
                    p.providerDependency.getType());
            paramGenerator.accept(type, p.providerDependency.getName());
        });
    }

    protected void generateConstructorBody(PrintWriter out) {

    }

    private void generateProviderConstructor(PrintWriter out) {
        var params = new StringBuilder();
        generateConstructorParams(new BiConsumer<>() {
            boolean needSep = false;

            @Override
            public void accept(String type, String name) {
                if (needSep) {
                    params.append(",");
                }
                needSep = true;
                params.append(type).append(" ").append(name);
            }
        });
        out.printf("\tpublic %s(%s) {\n", providerSimpleName, params);
        generateConstructorBody(out);
        out.print("\t}\n\n");
    }

    protected final String generateQualifierAnnotations(QualifierModel qualifier, boolean multiline) {
        if (qualifier == null || qualifier.isEmpty()) {
            return "";
        }
        var result = new StringBuilder(256);
        qualifier.forEach((qualifierName, props) -> {
            var strProps = props.stream()
                    .map(e -> String.format("@%s(name=\"%s\",value=\"%s\")",
                            QualifierProperty.class.getName(),
                            StringEscapeUtils.escapeJava(e.getLeft()),
                            StringEscapeUtils.escapeJava(e.getRight())))
                    .collect(Collectors.joining(",", "{", "}"));
            var qline = String.format("@%s(name=\"%s\", properties=%s)", Qualifier.class.getName(), qualifierName, props);
            result.append(qline);
            if (multiline) {
                result.append('\n');
            } else {
                result.append(' ');
            }
        });
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

    protected static class Dependency {
        protected final DependencyModel componentDependency;
        protected final DependencyModel providerDependency;

        public Dependency(DependencyModel componentDependency) {
            this.componentDependency = componentDependency;
            this.providerDependency = componentDependency.copyAsProvider();
        }
    }
}
