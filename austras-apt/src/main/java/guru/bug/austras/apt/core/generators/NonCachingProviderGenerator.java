package guru.bug.austras.apt.core.generators;

import guru.bug.austras.apt.model.ComponentModel;
import guru.bug.austras.apt.model.DependencyModel;
import guru.bug.austras.provider.Provider;

import javax.annotation.processing.ProcessingEnvironment;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class NonCachingProviderGenerator extends BaseProviderGenerator {

    public NonCachingProviderGenerator(ProcessingEnvironment processingEnv, ComponentModel componentModel, List<DependencyModel> dependencies) {
        super(processingEnv, componentModel, dependencies);
    }


    @Override
    protected void generateProviderFields(BiConsumer<String, String> fieldGenerator) {
        dependencies.forEach(p -> {
            var providerClass = String.format("%s<%s>", Provider.class.getName(), p.getType());
            var providerVar = p.getName() + "Provider";
            fieldGenerator.accept(providerClass, providerVar);
        });
    }

    @Override
    protected void generateConstructorBody(PrintWriter out) {
        dependencies.forEach(p -> {
            String varName = p.getName() + "Provider";
            out.printf("\t\tthis.%s = %s;\n", varName, varName);
        });
    }

    @Override
    protected void generateGetMethodBody(PrintWriter out) {
        dependencies.forEach(dm -> {
            out.printf("\t\tvar %s = this.%sProvider.get();\n", dm.getName(), dm.getName());
        });
        var params = dependencies.stream()
                .map(DependencyModel::getName)
                .collect(Collectors.joining(","));
        out.printf("\t\treturn new %s(%s);\n", componentModel.getInstantiable(), params);
    }

}
