package guru.bug.austras.apt.core.generators;

import guru.bug.austras.apt.model.ComponentModel;
import guru.bug.austras.apt.model.DependencyModel;

import javax.annotation.processing.ProcessingEnvironment;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class CachingProviderGenerator extends BaseProviderGenerator {

    private final String scopeType;
    private final String componentCacheVarName;

    public CachingProviderGenerator(ProcessingEnvironment processingEnv, ComponentModel componentModel, List<DependencyModel> dependencies, String scopeType, String componentCacheVarName) {
        super(processingEnv, componentModel, dependencies);
        this.scopeType = scopeType;
        this.componentCacheVarName = componentCacheVarName;
    }

    @Override
    protected void generateProviderFields(BiConsumer<String, String> fieldGenerator) {
        var cacheType = String.format("%s<%s>", scopeType, componentQualifiedName);
        fieldGenerator.accept(cacheType, componentCacheVarName);
    }

    @Override
    protected void generateGetMethodBody(PrintWriter out) {
        out.printf("\t\treturn this.%s.get();\n", componentCacheVarName);
    }

    @Override
    protected void generateConstructorBody(PrintWriter out) {
        out.printf("\t\tthis.%s = new %s<>(() -> {\n", componentCacheVarName, scopeType);
        dependencies.forEach(p -> out.printf("\t\t\tvar %1$s = %1$sProvider.get();\n", p.getName()));
        var params = dependencies.stream()
                .map(DependencyModel::getName)
                .collect(Collectors.joining(","));
        out.printf("\t\t\treturn new %s(%s);\n", componentQualifiedName, params);
        out.print("\t\t});\n");
    }


}
