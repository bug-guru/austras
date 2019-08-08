package guru.bug.austras.apt.core.generators;

import guru.bug.austras.apt.model.ComponentModel;
import guru.bug.austras.apt.model.DependencyModel;
import guru.bug.austras.provider.Provider;

import javax.annotation.processing.ProcessingEnvironment;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

public class EagerSingletonProviderGenerator extends BaseProviderGenerator {

    public EagerSingletonProviderGenerator(ProcessingEnvironment processingEnv, ComponentModel componentModel, List<DependencyModel> dependencies) {
        super(processingEnv, componentModel, dependencies);
    }


    @Override
    protected void generateProviderFields(PrintWriter out) {
        out.printf("\tprivate final %s %s;\n\n", componentModel.getInstantiable(), componentModel.getName());
    }

    @Override
    protected void generateProviderConstructor(PrintWriter out) {
        out.printf("\tpublic %s(", providerSimpleName);
        String params = dependencies.stream()
                .map(p -> String.format("%s%s<%s> %sProvider",
                        generateQualifierAnnotations(p.getQualifiers(), false),
                        Provider.class.getName(),
                        p.getType(),
                        p.getName()))
                .collect(Collectors.joining("," ));
        out.print(params);
        out.print(") {\n" );
        dependencies.forEach(p -> {
            out.printf("\t\tvar %1$s = %1$sProvider.get();\n", p.getName());
        });
        out.printf("\t\tthis.%s = new %s(", componentModel.getName(), componentSimpleName);
        String vars = dependencies.stream()
                .map(DependencyModel::getName)
                .collect(Collectors.joining("," ));
        out.print(vars);
        out.print(");\n" );
        out.print("\t}\n" );
    }

    @Override
    protected void generateGetInstance(PrintWriter out) {
        out.printf("\t@Override\n" );
        out.printf("\tpublic %s get() {\n", componentModel.getInstantiable());
        out.printf("\t\treturn this.%s;\n", componentModel.getName());
        out.print("\t}\n\n" );
    }
}
