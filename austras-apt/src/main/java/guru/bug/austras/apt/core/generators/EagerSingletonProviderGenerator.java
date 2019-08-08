package guru.bug.austras.apt.core.generators;

import guru.bug.austras.apt.model.ComponentModel;
import guru.bug.austras.apt.model.DependencyModel;

import javax.annotation.processing.ProcessingEnvironment;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class EagerSingletonProviderGenerator extends BaseProviderGenerator {

    public EagerSingletonProviderGenerator(ProcessingEnvironment processingEnv, ComponentModel componentModel, List<DependencyModel> dependencies) {
        super(processingEnv, componentModel, dependencies);
    }


    @Override
    protected void generateProviderFields(BiConsumer<String, String> fieldGenerator) {
        fieldGenerator.accept(componentModel.getInstantiable(), componentModel.getName());
    }

    @Override
    protected void generateGetMethodBody(PrintWriter out) {
        out.printf("\t\treturn this.%s;\n", componentModel.getName());
    }

    @Override
    protected void generateConstructorBody(PrintWriter out) {
        dependencies.forEach(p -> {
            out.printf("\t\tvar %1$s = %1$sProvider.get();\n", p.getName());
        });
        out.printf("\t\tthis.%s = new %s(", componentModel.getName(), componentSimpleName);
        String vars = dependencies.stream()
                .map(DependencyModel::getName)
                .collect(Collectors.joining(","));
        out.print(vars);
        out.print(");\n");
    }
}
