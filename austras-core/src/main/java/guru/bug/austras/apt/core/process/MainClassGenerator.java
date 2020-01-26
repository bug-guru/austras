/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.apt.core.process;

import guru.bug.austras.apt.core.common.model.ComponentKey;
import guru.bug.austras.apt.core.common.model.ComponentModel;
import guru.bug.austras.apt.core.common.model.DependencyModel;
import guru.bug.austras.apt.core.engine.ProcessingContext;
import guru.bug.austras.codegen.BodyProcessor;
import guru.bug.austras.codegen.JavaFileGenerator;
import guru.bug.austras.codegen.Template;
import guru.bug.austras.core.Instance;
import guru.bug.austras.core.Selector;
import guru.bug.austras.startup.ServiceManager;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

@Template(file = "Main.java.txt")
public class MainClassGenerator extends JavaFileGenerator {
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

    public MainClassGenerator(ProcessingContext ctx) {
        this.ctx = ctx;
    }

    @Template(name = "PACKAGE_NAME")
    @Override
    public String getPackageName() {
        return packageName;
    }

    @Template(name = "QUALIFIED_CLASS_NAME")
    public String getQualifiedClassName() {
        return qualifiedClassName;
    }

    @Template(name = "SIMPLE_CLASS_NAME")
    @Override
    public String getSimpleClassName() {
        return simpleClassName;
    }

    @Template(name = "COMPONENTS")
    public void componentsLoop(BodyProcessor body) {
        for (var c : sortedComponents) {
            this.currentComponent = c;
            body.process();
        }
    }

    @Template(name = "COMPONENT_NAME")
    public String getCurrentComponentProviderName() {
        return currentComponent.getName();
    }

    @Template(name = "COMPONENT_CLASS")
    public String getCurrentComponentProviderClass() {
        return tryImport(currentComponent.getInstantiable());
    }

    @Template(name = "WITH_DEPENDENCIES")
    public void withDependencies(BodyProcessor body) {
        if (!currentComponent.getDependencies().isEmpty()) {
            body.process();
        }
    }

    @Template(name = "WITHOUT_DEPENDENCIES")
    public void withoutDependencies(BodyProcessor body) {
        if (currentComponent.getDependencies().isEmpty()) {
            body.process();
        }
    }

    @Template(name = "COMPONENT_DEPENDENCIES")
    public void currentComponentCollectionsInitializers(BodyProcessor body) {

        Iterator<DependencyModel> dependencies = currentComponent.getDependencies().iterator();
        while (dependencies.hasNext()) {
            this.currentDependency = dependencies.next();
            this.hasMoreDependencies = dependencies.hasNext();
            body.process();
        }
    }

    @Template(name = ",")
    public String dependenciesSeparator() {
        if (this.hasMoreDependencies) {
            return ",";
        } else {
            return "";
        }
    }

    @Template(name = "DEPENDENCY")
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

    @Template(name = "STARTER_COMPONENT_NAME")
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
        var unresolved = new HashSet<DependencyModel>();

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
                unresolved.addAll(ref.unresolved);
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
        generate(ctx.processingEnv().getFiler());
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

        @Override
        public String toString() {
            return Objects.toString(component);
        }
    }


}
