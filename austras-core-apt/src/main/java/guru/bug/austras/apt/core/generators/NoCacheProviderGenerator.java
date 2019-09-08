package guru.bug.austras.apt.core.generators;

import guru.bug.austras.apt.model.ComponentModel;
import guru.bug.austras.apt.model.DependencyModel;

import javax.annotation.processing.ProcessingEnvironment;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class NoCacheProviderGenerator extends BaseProviderGenerator {

    public NoCacheProviderGenerator(ProcessingEnvironment processingEnv, ComponentModel componentModel, List<DependencyModel> dependencies) {
        super(processingEnv, componentModel, dependencies);
    }


    @Override
    protected void generateProviderFields(BiConsumer<String, String> fieldGenerator) {
        providerDependencies.forEach(p -> fieldGenerator.accept(p.providerDependency.getType(), p.providerDependency.getName()));
    }

    @Override
    protected void generateConstructorBody(PrintWriter out) {
        providerDependencies.forEach(p -> out.printf("\t\tthis.%1$s = %1$s;\n", p.providerDependency.getName()));
    }

    @Override
    protected void generateGetMethodBody(PrintWriter out) {
        providerDependencies.forEach(dm -> {
            out.printf("\t\tvar %s = this.%s.get();\n", dm.componentDependency.getName(), dm.providerDependency.getName());
        });
        var params = providerDependencies.stream()
                .map(p -> p.componentDependency.getName())
                .collect(Collectors.joining(","));
        out.printf("\t\treturn new %s(%s);\n", componentModel.getInstantiable(), params);
    }

}
