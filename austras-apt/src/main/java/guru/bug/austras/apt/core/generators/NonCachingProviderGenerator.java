package guru.bug.austras.apt.core.generators;

import guru.bug.austras.apt.model.ComponentModel;
import guru.bug.austras.apt.model.DependencyModel;
import guru.bug.austras.provider.Provider;

import javax.annotation.processing.ProcessingEnvironment;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

public class NonCachingProviderGenerator extends BaseProviderGenerator {

    public NonCachingProviderGenerator(ProcessingEnvironment processingEnv, ComponentModel componentModel, List<DependencyModel> dependencies) {
        super(processingEnv, componentModel, dependencies);
    }


    @Override
    protected void generateProviderFields(PrintWriter out) {
        dependencies.forEach(p -> {
            out.printf("\tprivate final %s<%s> %s;\n",
                    Provider.class.getName(),
                    p.getType(),
                    p.getName() + "Provider" );
        });
    }

    @Override
    protected void generateProviderConstructor(PrintWriter out) {
        out.printf("\tpublic %s(", providerSimpleName);
        String params = dependencies.stream()
                .map(p -> String.format("%s<%s> %sProvider",
                        Provider.class.getName(),
                        p.getType(),
                        p.getName()))
                .collect(Collectors.joining("," ));
        out.print(params);
        out.print(") {\n" );
        dependencies.forEach(p -> {
            String varName = p.getName() + "Provider";
            out.printf("\t\tthis.%s = %s;\n", varName, varName);
        });
        out.print("\t}\n" );
    }

    @Override
    protected void generateGetInstance(PrintWriter out) {
        out.printf("\t@Override\n" );
        out.printf("\tpublic %s get() {\n", componentModel.getInstantiable());
        dependencies.forEach(dm -> {
            out.printf("\t\tvar %s = this.%sProvider.get();\n", dm.getName(), dm.getName());
        });
        var params = dependencies.stream()
                .map(DependencyModel::getName)
                .collect(Collectors.joining("," ));
        out.printf("\t\treturn new %s(%s);\n", componentModel.getInstantiable(), params);
        out.print("\t}\n\n" );
    }

}
