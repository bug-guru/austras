package guru.bug.austras.apt.core.process;

import guru.bug.austras.apt.core.common.model.ComponentKey;
import guru.bug.austras.apt.core.common.model.ComponentModel;
import guru.bug.austras.apt.core.common.model.DependencyModel;
import guru.bug.austras.apt.core.engine.ProcessingContext;
import guru.bug.austras.codegen.BodyBlock;
import guru.bug.austras.codegen.FromTemplate;
import guru.bug.austras.codegen.JavaGenerator;
import guru.bug.austras.codegen.TemplateException;
import guru.bug.austras.core.Instance;
import guru.bug.austras.core.Selector;
import guru.bug.austras.startup.ServiceManager;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

@FromTemplate("Main.java.txt")
public class MainClassGenerator extends JavaGenerator {
    private static final Logger log = LoggerFactory.getLogger(MainClassGenerator.class);
    private final ProcessingContext ctx;
    private String qualifiedClassName;
    private String simpleClassName;
    private String packageName;
    private ComponentModel starterComponent;
    private ComponentModel currentComponent;
    private DependencyModel currentDependency;
    private boolean hasMoreDependencies;
    private List<ComponentModel> sortedComponents;

    public MainClassGenerator(ProcessingContext ctx) throws IOException, TemplateException {
        super(ctx.processingEnv().getFiler());
        this.ctx = ctx;
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

    @FromTemplate("COMPONENT_NAME")
    public String getCurrentComponentProviderName() {
        return currentComponent.getName();
    }

    @FromTemplate("COMPONENT_CLASS")
    public String getCurrentComponentProviderClass() {
        return tryImport(currentComponent.getInstantiable());
    }

    @FromTemplate("WITH_DEPENDENCIES")
    public void withDependencies(PrintWriter out, BodyBlock bodyBlock) {
        if (!currentComponent.getDependencies().isEmpty()) {
            out.print(bodyBlock.evaluateBody());
        }
    }

    @FromTemplate("WITHOUT_DEPENDENCIES")
    public void withoutDependencies(PrintWriter out, BodyBlock bodyBlock) {
        if (currentComponent.getDependencies().isEmpty()) {
            out.print(bodyBlock.evaluateBody());
        }
    }

    @FromTemplate("COMPONENT_DEPENDENCIES")
    public void currentComponentCollectionsInitializers(PrintWriter out, BodyBlock bodyBlock) {

        Iterator<DependencyModel> dependencies = currentComponent.getDependencies().iterator();
        while (dependencies.hasNext()) {
            this.currentDependency = dependencies.next();
            this.hasMoreDependencies = dependencies.hasNext();
            out.print(bodyBlock.evaluateBody());
        }
    }

    @FromTemplate(",")
    public String dependenciesSeparator() {
        if (this.hasMoreDependencies) {
            return ",";
        } else {
            return "";
        }
    }

    @FromTemplate("DEPENDENCY")
    public String dependency() {
        switch (currentDependency.getWrapping()) {
            case NONE:
                return ctx.componentManager()
                        .findSingleComponent(currentDependency)
                        .map(ComponentModel::getName)
                        .orElseThrow();
            case COLLECTION:
                return ctx.componentManager()
                        .findComponents(currentDependency)
                        .stream()
                        .map(ComponentModel::getName)
                        .collect(Collectors.joining(", ", tryImport(List.class.getName()) + ".of(", ")"));
            case SELECTOR:
                return ctx.componentManager()
                        .findComponents(currentDependency)
                        .stream()
                        .map(this::convertToInstance)
                        .collect(Collectors.joining(", ", tryImport(Selector.class.getName()) + ".of(", ")"));
            default:
                throw new IllegalArgumentException("Unknown wrapping " + currentDependency.getWrapping());
        }
    }

    private String convertToInstance(ComponentModel componentModel) {
        var result = new StringBuilder();
        result.append(tryImport(Instance.class.getName()))
                .append(".of(")
                .append(componentModel.getName());
        if (!componentModel.getQualifiers().isEmpty()) {
            result.append(", sb -> sb");
            componentModel.getQualifiers().getAll().forEach(q -> {
                result.append(".add(");
                stringConst(result, q.getName());
                if (!q.getProperties().isEmpty()) {
                    result.append(", qb -> qb");
                    for (var p : q.getProperties()) {
                        result.append(".add(");
                        stringConst(result, p.getName());
                        result.append(", ");
                        stringConst(result, p.getValue());
                        result.append(")");
                    }
                }
                result.append(")");
            });
        }
        result.append(")");
        return result.toString();
    }

    private void stringConst(StringBuilder result, String value) {
        result.append('"').append(StringEscapeUtils.escapeJava(value)).append('"');
    }

    @FromTemplate("STARTER_COMPONENT_NAME")
    public String getStarterName() {
        return starterComponent.getName();
    }

    private List<ComponentModel> sortComponents() {
        var resolved = new LinkedHashSet<ComponentModel>();
        var queue = findRequiredComponents();
        while (!queue.isEmpty()) {
            var ref = queue.remove();
            if (ref.dependencies.isEmpty() || resolved.containsAll(ref.dependencies)) {
                resolved.add(ref.component);
            } else {
                queue.add(ref);
            }
        }
        return List.copyOf(resolved);
    }


    private Queue<ComponentModelRef> findRequiredComponents() {
        var result = new HashMap<ComponentModel, ComponentModelRef>();
        var unresolved = new HashSet<ComponentModelRef>();

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
            if (!ref.unresolved.isEmpty()) {
                unresolved.add(ref);
            }
        }

        if (!unresolved.isEmpty()) {
            log.error("Unresolved components: {}", unresolved);
            throw new IllegalStateException("Unresolved components");
        }

        return new ArrayDeque<>(result.values());
    }

    public void generateAppMain(ComponentModel appMainComponent) {
        if (appMainComponent == null) {
            log.debug("No application component");
            return;
        }
        var starterKey = ComponentKey.of(ServiceManager.class, null);
        this.starterComponent = ctx.componentManager().findSingleComponent(starterKey).orElseThrow();
        this.sortedComponents = sortComponents();
        this.qualifiedClassName = appMainComponent.getInstantiable() + "Main";
        var lastDotIndex = qualifiedClassName.lastIndexOf('.');
        this.simpleClassName = qualifiedClassName.substring(lastDotIndex + 1);
        this.packageName = qualifiedClassName.substring(0, lastDotIndex);
        generateJavaClass();
    }

    private class ComponentModelRef {
        final ComponentModel component;
        final Set<ComponentModel> dependencies;
        final Set<DependencyModel> unresolved;

        private ComponentModelRef(ComponentModel component) {
            this.component = Objects.requireNonNull(component, "component");
            var deps = Objects.requireNonNull(component.getDependencies(), "dependencies for " + component);
            this.unresolved = new HashSet<>();
            this.dependencies = deps.stream()
                    .flatMap(d -> {
                        var comps = ctx.componentManager().findComponents(d);
                        if (comps.isEmpty()) {
                            unresolved.add(d);
                        }
                        return comps.stream();
                    })
                    .collect(Collectors.toSet());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ComponentModelRef that = (ComponentModelRef) o;
            return component.equals(that.component);
        }

        @Override
        public int hashCode() {
            return Objects.hash(component);
        }
    }


}
