package guru.bug.austras.apt.core.process;

import guru.bug.austras.apt.core.ComponentMap;
import guru.bug.austras.apt.core.model.ComponentKey;
import guru.bug.austras.apt.core.model.ComponentModel;
import guru.bug.austras.apt.core.model.DependencyModel;
import guru.bug.austras.apt.core.model.ProviderModel;
import guru.bug.austras.codegen.BodyBlock;
import guru.bug.austras.codegen.FromTemplate;
import guru.bug.austras.codegen.JavaGenerator;
import guru.bug.austras.codegen.TemplateException;
import guru.bug.austras.startup.StartupServicesStarter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.Filer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

@FromTemplate("Main.java.txt")
public class MainClassGenerator extends JavaGenerator {
    private static final Logger log = LoggerFactory.getLogger(MainClassGenerator.class);
    private ComponentMap componentMap;
    private String qualifiedClassName;
    private String simpleClassName;
    private String packageName;
    private ComponentModel starterComponent;
    private ComponentModel currentComponent;
    private DependencyModel currentDependency;
    private String dependencyInitialization;
    private boolean hasMoreDependencies;
    private List<ComponentModel> sortedComponents;

    public MainClassGenerator(Filer filer) throws IOException, TemplateException {
        super(filer);
    }

    @FromTemplate("PACKAGE_NAME")
    @Override
    public String getPackageName() {
        return packageName;
    }

    @FromTemplate("QUALIFIED_CLASS_NAME")
    public String getQualifiedClassName() {
        return qualifiedClassName;
    }

    @FromTemplate("SIMPLE_CLASS_NAME")
    @Override
    public String getSimpleClassName() {
        return simpleClassName;
    }

    @FromTemplate("COMPONENTS")
    public void componentsLoop(PrintWriter out, BodyBlock bodyBlock) {
        for (var c : sortedComponents) {
            this.currentComponent = c;
            out.print(bodyBlock.evaluateBody());
        }
    }

    @FromTemplate("COMPONENT_PROVIDER_NAME")
    public String getCurrentComponentProviderName() {
        return currentComponent.getProvider().getName();
    }

    @FromTemplate("COMPONENT_PROVIDER_CLASS")
    public String getCurrentComponentProviderClass() {
        return tryImport(currentComponent.getProvider().getInstantiable());
    }

    @FromTemplate("COMPONENT_PROVIDER_VAR")
    public String getCurrentComponentProviderVar() {
        return currentComponent.getProvider().getName();
    }

    @FromTemplate("COMPONENT_NAME")
    public String getCurrentComponentName() {
        return currentComponent.getName();
    }

    @FromTemplate("WITH_DEPENDENCIES")
    public void withDependencies(PrintWriter out, BodyBlock bodyBlock) {
        if (!currentComponent.getProvider().getDependencies().isEmpty()) {
            out.print(bodyBlock.evaluateBody());
        }
    }

    @FromTemplate("WITHOUT_DEPENDENCIES")
    public void withoutDependencies(PrintWriter out, BodyBlock bodyBlock) {
        if (currentComponent.getProvider().getDependencies().isEmpty()) {
            out.print(bodyBlock.evaluateBody());
        }
    }

    @FromTemplate("COMPONENT_PROVIDER_DEPENDENCIES")
    public void currentComponentCollectionsInitializers(PrintWriter out, BodyBlock bodyBlock) {

        Iterator<DependencyModel> dependencies = currentComponent.getProvider().getDependencies().iterator();
        while (dependencies.hasNext()) {
            this.currentDependency = dependencies.next();
            this.hasMoreDependencies = dependencies.hasNext();
            out.print(bodyBlock.evaluateBody());
        }
    }

    @FromTemplate("OPTIONAL_COMMA_SEPARATOR")
    public String dependenciesSeparator() {
        if (this.hasMoreDependencies) {
            return ",";
        } else {
            return "";
        }
    }

    @FromTemplate("COLLECTION_DEPENDENCY")
    public void collectionDependency(PrintWriter out, BodyBlock bodyBlock) {
        if (currentDependency.isCollection() && currentDependency.isProvider()) {
            var key = new ComponentKey(currentDependency.getType(), currentDependency.getQualifiers());
            var params = componentMap.findComponentModels(key);
            dependencyInitialization = params.stream()
                    .map(p -> p.getProvider().getName())
                    .collect(Collectors.joining(", "));
            out.print(bodyBlock.evaluateBody());
            dependencyInitialization = null;
        }
    }

    @FromTemplate("UNWRAPPED_COLLECTION_DEPENDENCY")
    public void unwrappedCollectionDependency(PrintWriter out, BodyBlock bodyBlock) {
        if (currentDependency.isCollection() && !currentDependency.isProvider()) {
            var key = new ComponentKey(currentDependency.getType(), currentDependency.getQualifiers());
            var params = componentMap.findComponentModels(key);
            dependencyInitialization = params.stream()
                    .map(p -> p.getProvider().getName() + ".get()")
                    .collect(Collectors.joining(", "));
            out.print(bodyBlock.evaluateBody());
            dependencyInitialization = null;
        }
    }

    @FromTemplate("STANDARD_DEPENDENCY")
    public void standardDependency(PrintWriter out, BodyBlock bodyBlock) {
        if (!currentDependency.isCollection() && currentDependency.isProvider()) {
            var provider = findProviderModel();
            dependencyInitialization = provider.getName();
            out.print(bodyBlock.evaluateBody());
            dependencyInitialization = null;
        }
    }

    @FromTemplate("UNWRAPPED_STANDARD_DEPENDENCY")
    public void unwrappedStandardDependency(PrintWriter out, BodyBlock bodyBlock) {
        if (!currentDependency.isCollection() && !currentDependency.isProvider()) {
            var provider = findProviderModel();
            dependencyInitialization = provider.getName() + ".get()";
            out.print(bodyBlock.evaluateBody());
            dependencyInitialization = null;
        }
    }

    private ProviderModel findProviderModel() {
        var key = new ComponentKey(currentDependency.getType(), currentDependency.getQualifiers());
        ComponentModel singleComponentModel = componentMap.findSingleComponentModel(key);
        if (singleComponentModel == null) {
            throw new IllegalStateException("Component " + key + " not found"); // TODO
        }
        ProviderModel provider = singleComponentModel.getProvider();
        if (provider == null) {
            throw new IllegalStateException("Provider not found for component " + key + " not found"); // TODO
        }
        return provider;
    }

    @FromTemplate("DEPENDENCY_INITIALIZATION")
    public String getDependencyInitialization() {
        return dependencyInitialization;
    }

    @FromTemplate("STARTER_NAME")
    public String getStarterName() {
        return starterComponent.getName();
    }

    @FromTemplate("STARTER_PROVIDER_NAME")
    public String getStarterProviderName() {
        return starterComponent.getProvider().getName();
    }

    private List<ComponentModel> sortComponents() {
        var result = new ArrayList<ComponentModel>();
        var resolved = Collections.<ComponentModel>newSetFromMap(new IdentityHashMap<>());
        var queue = findRequiredComponents();
        while (!queue.isEmpty()) {
            var ref = queue.remove();
            if (ref.dependencies.isEmpty() || resolved.containsAll(ref.dependencies)) {
                resolved.add(ref.component);
                result.add(ref.component);
            } else {
                queue.add(ref);
            }
        }
        return result;
    }


    private Queue<ComponentModelRef> findRequiredComponents() {
        var result = new IdentityHashMap<ComponentModel, ComponentModelRef>();

        Queue<ComponentModel> resolveQueue = new LinkedList<>();
        resolveQueue.add(starterComponent);

        while (!resolveQueue.isEmpty()) {
            var comp = resolveQueue.remove();
            if (result.containsKey(comp)) {
                continue;
            }

            var ref = new ComponentModelRef(comp);
            resolveQueue.addAll(ref.dependencies);
            result.put(comp, ref);
        }

        return new ArrayDeque<>(result.values());
    }

    public void generateAppMain(ComponentModel appMainComponent, ComponentMap componentMap) {
        if (appMainComponent == null) {
            log.debug("No application component");
            return;
        }
        this.componentMap = componentMap;
        var starterKey = new ComponentKey(StartupServicesStarter.class.getName(), null);
        this.starterComponent = componentMap.findSingleComponentModel(starterKey);
        this.sortedComponents = sortComponents();
        this.qualifiedClassName = appMainComponent.getInstantiable() + "Main";
        var lastDotIndex = qualifiedClassName.lastIndexOf('.');
        this.simpleClassName = qualifiedClassName.substring(lastDotIndex + 1);
        this.packageName = qualifiedClassName.substring(0, lastDotIndex);
        generateJavaClass();
    }

    private class ComponentModelRef {
        final ComponentModel component;
        final List<ComponentModel> dependencies;

        private ComponentModelRef(ComponentModel component) {
            this.component = component;
            this.dependencies = component.getProvider().getDependencies().stream()
                    .map(DependencyModel::asComponentKey)
                    .flatMap(k -> componentMap.findComponentModels(k).stream())
                    .collect(Collectors.toList());
        }
    }


}
