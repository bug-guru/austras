package guru.bug.austras.apt.core.generators;

import guru.bug.austras.apt.core.componentmap.ComponentKey;
import guru.bug.austras.apt.core.componentmap.ComponentMap;
import guru.bug.austras.apt.core.componentmap.UniqueNameGenerator;
import guru.bug.austras.apt.model.ComponentModel;
import guru.bug.austras.apt.model.DependencyModel;
import guru.bug.austras.apt.model.ProviderModel;
import guru.bug.austras.provider.CollectionProvider;
import org.apache.commons.text.StringEscapeUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MainClassGenerator {
    private static final Logger log = Logger.getLogger(MainClassGenerator.class.getName());
    private final UniqueNameGenerator uniqueNameGenerator;
    private final ProcessingEnvironment processingEnv;
    private final ComponentMap componentMap;
    private final Elements elementUtils;
    private final String loggerVarName;
    private ComponentModel appComponentModel;

    public MainClassGenerator(ProcessingEnvironment processingEnv, ComponentMap componentMap, UniqueNameGenerator uniqueNameGenerator) {
        this.processingEnv = processingEnv;
        this.componentMap = componentMap;
        this.elementUtils = processingEnv.getElementUtils();
        this.uniqueNameGenerator = uniqueNameGenerator;
        this.loggerVarName = uniqueNameGenerator.findFreeVarName("log");
    }

    public void setAppComponentModel(ComponentModel appComponentModel) {
        if (this.appComponentModel != null) {
            throw new IllegalStateException("Application already defined");
        }
        this.appComponentModel = appComponentModel;
    }

    public void generateAppMain() {
        if (appComponentModel == null) {
            log.fine("No application component");
            return;
        }
        var l = java.util.logging.Logger.getGlobal();
        l.info(() -> "");
        var sortedComponents = sortComponents();
        var appInstantiable = appComponentModel.getInstantiable();
        var mainClassQualifiedName = appInstantiable + "Main";
        var lastDotIndex = mainClassQualifiedName.lastIndexOf('.');
        var mainClassSimpleName = mainClassQualifiedName.substring(lastDotIndex + 1);
        var packageName = mainClassQualifiedName.substring(0, lastDotIndex);
        try (var out = new PrintWriter(processingEnv.getFiler().createSourceFile(mainClassQualifiedName).openWriter())) {
            out.printf("package %s;\n", packageName);
            out.printf("public class %s {\n", mainClassSimpleName);
            out.printf("\tprivate static final java.util.logging.Logger %s = java.util.logging.Logger.getLogger(\"%s\");\n", loggerVarName, mainClassQualifiedName);
            out.print("\tpublic static void main(String... args) {\n");
            genInfoLog(out, "Application is initializing...");
            sortedComponents.forEach(m -> generateProviderCall(m, out));
            generateServicesCall(out);
            genInfoLog(out, "Application is ready!");
            out.write("\t}\n");
            out.write("}\n");
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void generateServicesCall(PrintWriter out) {
        genInfoLog(out, "Starting up services...");

        genInfoLog(out, "Services are started!");
    }

    private void genInfoLog(PrintWriter out, String msg) {
        out.printf("\t\t%s.info(() -> \"%s\");\n", loggerVarName, StringEscapeUtils.escapeJava(msg));
    }

    private void generateProviderCall(ComponentModel componentModel, PrintWriter out) {
        ProviderModel provider = componentModel.getProvider();
        genInfoLog(out, "Initializing provider " + provider.getInstantiable() + " for " + componentModel.getInstantiable());
        var initializers = provider.getDependencies().stream()
                .map(this::createInitializer)
                .peek(i -> i.init(out))
                .collect(Collectors.toList());
        var params = initializers.stream()
                .map(ParamInitializer::getAsParameter)
                .collect(Collectors.joining(", "));
        out.write(String.format("\t\tvar %s = new %s(%s);\n", provider.getName(), provider.getInstantiable(), params));
    }

    private List<ComponentModel> sortComponents() {
        var result = new ArrayList<ComponentModel>();
        var unresolved = new LinkedList<>(componentMap.getKeys());
        var resolved = Collections.<ComponentModel>newSetFromMap(new IdentityHashMap<>());
        outer:
        while (!unresolved.isEmpty()) {
            var key = unresolved.remove();
            var components = Collections.<ComponentModel>newSetFromMap(new IdentityHashMap<>());
            components.addAll(componentMap.findComponentModels(key));
            for (var comp : components) {
                ProviderModel provider = Objects.requireNonNull(comp.getProvider(), () -> String.format("Component %s doesn't have a provider", comp));
                List<DependencyModel> dependencies = Objects.requireNonNull(provider.getDependencies());
                var hasUnresolved = dependencies.stream()
                        .map(d -> new ComponentKey(d.getType(), d.getQualifiers()))
                        .anyMatch(unresolved::contains);
                if (hasUnresolved) {
                    unresolved.add(key);
                    continue outer;
                }
            }
            components.removeAll(resolved);
            resolved.addAll(components);
            result.addAll(components);
        }
        return result;
    }


    private ParamInitializer createInitializer(DependencyModel model) {
        ParamInitializer result;
        if (model.isCollection()) {
            result = new CollectionParamInitializer(model);
        } else {
            result = new StandardParamInitializer(model);
        }
        if (!model.isProvider()) {
            result = new ProviderUnwrappedParamInitializer(result);
        }
        return result;
    }

    private interface ParamInitializer {
        void init(PrintWriter out);

        String getAsParameter();
    }

    private class ProviderUnwrappedParamInitializer implements ParamInitializer {
        private final ParamInitializer initializer;

        ProviderUnwrappedParamInitializer(ParamInitializer initializer) {
            this.initializer = initializer;
        }

        @Override
        public void init(PrintWriter out) {
            initializer.init(out);
        }

        @Override
        public String getAsParameter() {
            return initializer.getAsParameter() + ".get()";
        }
    }

    private abstract class AbstractParamInitializer implements ParamInitializer {
        final DependencyModel dependencyModel;
        final ComponentKey key;

        AbstractParamInitializer(DependencyModel dependencyModel) {
            this.dependencyModel = dependencyModel;
            this.key = new ComponentKey(dependencyModel.getType(), dependencyModel.getQualifiers());
        }

        @Override
        public void init(PrintWriter out) {

        }

    }

    private class StandardParamInitializer extends AbstractParamInitializer {

        StandardParamInitializer(DependencyModel dependencyModel) {
            super(dependencyModel);
        }

        @Override
        public String getAsParameter() {
            ComponentModel singleComponentModel = componentMap.findSingleComponentModel(key);
            if (singleComponentModel == null) {
                throw new IllegalStateException("Component " + key + " not found"); // TODO
            }
            ProviderModel provider = singleComponentModel.getProvider();
            if (provider == null) {
                throw new IllegalStateException("Provider not found for component " + key + " not found"); // TODO
            }
            return provider.getName();
        }
    }

    private class CollectionParamInitializer extends AbstractParamInitializer {
        final String name;

        CollectionParamInitializer(DependencyModel dependencyModel) {
            super(dependencyModel);
            this.name = uniqueNameGenerator.findFreeVarName(dependencyModel.getName() + "Collection");
        }

        @Override
        public void init(PrintWriter out) {
            var params = componentMap.findComponentModels(key).stream()
                    .map(c -> c.getProvider().getName())
                    .collect(Collectors.joining(", "));
            out.printf("\t\tvar %s = new %s<>(%s);\n", name, CollectionProvider.class.getName(), params);
        }

        @Override
        public String getAsParameter() {
            return name;
        }
    }

}
