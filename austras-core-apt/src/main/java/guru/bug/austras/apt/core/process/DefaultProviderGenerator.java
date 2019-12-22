package guru.bug.austras.apt.core.process;

import guru.bug.austras.apt.core.ModelUtils;
import guru.bug.austras.apt.core.model.ComponentModel;
import guru.bug.austras.apt.core.model.DependencyModel;
import guru.bug.austras.codegen.BodyBlock;
import guru.bug.austras.codegen.FromTemplate;
import guru.bug.austras.codegen.JavaGenerator;
import guru.bug.austras.codegen.TemplateException;
import guru.bug.austras.core.Provider;
import guru.bug.austras.core.Selector;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@FromTemplate("DefaultProvider.java.txt")
public class DefaultProviderGenerator extends JavaGenerator {
    private final ProcessingEnvironment processingEnv;
    private String providerSimpleClassName;
    private String providerPackageName;
    private List<DependencyModel> dependencies;
    private ComponentModel componentModel;
    private DependencyModel currentDependency;
    private String optionalComma;
    private String currentQualifierName;
    private List<Pair<String, String>> currentQualifierProperties;
    private Pair<String, String> currentQualifierProperty;

    public DefaultProviderGenerator(ProcessingEnvironment processingEnv) throws IOException, TemplateException {
        super(processingEnv.getFiler());
        this.processingEnv = processingEnv;
    }

    public void generate(ComponentModel componentModel, TypeElement element, List<DependencyModel> dependencies) {
        this.componentModel = componentModel;
        this.providerPackageName = processingEnv.getElementUtils().getPackageOf(element).getQualifiedName().toString();
        this.providerSimpleClassName = element.getSimpleName() + "Provider";
        this.dependencies = dependencies;
        super.generateJavaClass();
    }

    @FromTemplate("QUALIFIER_ANNOTATIONS")
    public String componentQualifiersAnnotations() {
        return ModelUtils.qualifierToString(componentModel.getQualifiers());
    }

    @FromTemplate("COMPONENT_CLASS_NAME")
    public String componentClassName() {
        return tryImport(componentModel.getInstantiable());
    }

    @FromTemplate("COMPONENT_NAME")
    public String componentName() {
        return componentModel.getName();
    }

    @FromTemplate("PACKAGE_NAME")
    @Override
    public String getPackageName() {
        return providerPackageName;
    }

    @FromTemplate("SIMPLE_CLASS_NAME")
    @Override
    public String getSimpleClassName() {
        return providerSimpleClassName;
    }

    @FromTemplate("DEPENDENCIES")
    public void dependencies(PrintWriter out, BodyBlock body) {
        var i = dependencies.iterator();
        while (i.hasNext()) {
            currentDependency = i.next();
            optionalComma = i.hasNext() ? ", " : "";
            out.print(body.evaluateBody());
        }
    }

    @FromTemplate(",")
    public String optionalComma() {
        return optionalComma;
    }

    @FromTemplate("DEPENDENCY_QUALIFIERS")
    public String dependencyQualifiers() {
        return ModelUtils.qualifierToString(currentDependency.getQualifiers());
    }

    @FromTemplate("DEPENDENCY_PROVIDER_TYPE")
    public String dependencyType() {
        var compType = tryImport(currentDependency.getType());
        switch (currentDependency.getWrappingType()) {
            case NONE:
                throw new IllegalArgumentException("Must be provider");
            case SELECTOR:
                return tryImport(Selector.class.getName()) + "<? extends " + compType + ">";
            case PROVIDER:
                return tryImport(Provider.class.getName()) + "<? extends " + compType + ">";
            default:
                throw new IllegalArgumentException("Unsupported " + currentDependency.getWrappingType());
        }
    }

    @FromTemplate("DEPENDENCY_PROVIDER_NAME")
    public String dependencyName() {
        return currentDependency.providerDependency.getName();
    }

    @FromTemplate("DEPENDENCY_RESOLVE")
    public void dependencyResolve(PrintWriter out) {
        var providerDependency = currentDependency.providerDependency;
        var componentDependency = currentDependency.componentDependency;
        var compType = tryImport(providerDependency.getType());
        switch (componentDependency.getWrappingType()) {
            case NONE:
                throw new IllegalArgumentException("Must be provider");
            case SELECTOR:
                return tryImport(Selector.class.getName()) + "<? extends " + compType + ">";
            case PROVIDER:
                return tryImport(Provider.class.getName()) + "<? extends " + compType + ">";
            default:
                throw new IllegalArgumentException("Unsupported " + providerDependency.getWrappingType());
        }
    }

    @FromTemplate("QUALIFIERS")
    public void qualifiers(PrintWriter out, BodyBlock body) {
        componentModel.getQualifiers().forEach((name, props) -> {
            this.currentQualifierName = name;
            this.currentQualifierProperties = props;
            out.print(body.evaluateBody());
        });
    }

    @FromTemplate("QUALIFIER_NAME")
    public String qualifierName() {
        return currentQualifierName;
    }

    @FromTemplate("QUALIFIER_PROPERTIES")
    public void qualifierName(PrintWriter out, BodyBlock body) {
        currentQualifierProperties.forEach(pair -> {
            this.currentQualifierProperty = pair;
            out.print(body.evaluateBody());
        });
    }

    @FromTemplate("PROPERTY_KEY")
    public String propertyKey() {
        return currentQualifierProperty.getKey();
    }

    @FromTemplate("PROPERTY_VALUE")
    public String propertyValue() {
        return currentQualifierProperty.getValue();
    }

}
