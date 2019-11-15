package guru.bug.austras.apt.core.generators;

import guru.bug.austras.apt.model.DependencyModel;
import guru.bug.austras.codegen.FromTemplate;
import guru.bug.austras.codegen.JavaGenerator;

import javax.annotation.processing.Filer;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.List;

public class DefaultProviderGenerator extends JavaGenerator {


    public DefaultProviderGenerator(Filer filer) throws IOException {
        super(filer);
    }

    public void generate(TypeElement element, List<DependencyModel> dependencies) throws IOException {
        // TODO
        super.generateJavaClass();
    }


    @FromTemplate("PACKAGE_NAME")
    @Override
    public String getPackageName() {
        return null;
    }

    @FromTemplate("SIMPLE_CLASS_NAME")
    @Override
    public String getSimpleClassName() {
        return null;
    }
}
