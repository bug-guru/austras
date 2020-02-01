/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.apt.core.common.model.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.SimpleElementVisitor9;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BeanModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanModel.class);
    private static final PropExtractor propExtractor = new PropExtractor();
    private final DeclaredType beanType;
    private final Map<String, BeanPropertyModel> properties;

    public BeanModel(DeclaredType beanType, Set<BeanPropertyModel> properties) {
        this.beanType = beanType;
        this.properties = properties.stream()
                .collect(Collectors.toUnmodifiableMap(BeanPropertyModel::getName, Function.identity()));
    }

    public static BeanModel of(DeclaredType beanType) {
        var props = collectProps(beanType);
        return new BeanModel(beanType, props);
    }

    private static Set<BeanPropertyModel> collectProps(DeclaredType type) {
        var index = new HashMap<String, BeanPropertyModel.Builder>();
        for (var e : type.asElement().getEnclosedElements()) {
            e.accept(propExtractor, index);
        }
        return index.values().stream()
                .map(BeanPropertyModel.Builder::build)
                .collect(Collectors.toSet());
    }

    public DeclaredType getBeanType() {
        return beanType;
    }

    public Collection<BeanPropertyModel> getProperties() {
        return properties.values();
    }

    @SuppressWarnings("squid:MaximumInheritanceDepth")
    private static class PropExtractor extends SimpleElementVisitor9<Void, Map<String, BeanPropertyModel.Builder>> {
        @Override
        public Void visitVariable(VariableElement e, Map<String, BeanPropertyModel.Builder> index) {
            if (e.getKind() != ElementKind.FIELD) {
                return null;
            }
            if (e.getModifiers().contains(Modifier.STATIC)) {
                return null;
            }
            var name = e.getSimpleName().toString();
            var prop = getPropertyBuilderByName(index, name);
            prop.field(name, e.asType(), e);
            return null;
        }

        @Override
        public Void visitExecutable(ExecutableElement e, Map<String, BeanPropertyModel.Builder> index) {
            if (e.getKind() != ElementKind.METHOD) {
                return null;
            }
            if (e.getModifiers().contains(Modifier.STATIC)) {
                return null;
            }
            if (!e.getModifiers().contains(Modifier.PUBLIC)) {
                return null;
            }
            var methodName = e.getSimpleName().toString();
            var isVoid = e.getReturnType().getKind() == TypeKind.VOID;
            var params = e.getParameters();
            if (methodName.startsWith("get") && params.isEmpty() && !isVoid) {
                var name = propNameFromMethodName(methodName);
                var prop = getPropertyBuilderByName(index, name);
                prop.getter(name, e.getReturnType(), e);
            } else if (methodName.startsWith("set") && params.size() == 1 && isVoid) {
                var name = propNameFromMethodName(methodName);
                var prop = getPropertyBuilderByName(index, name);
                prop.setter(name, params.get(0).asType(), e);
            }
            return null;
        }

        private BeanPropertyModel.Builder getPropertyBuilderByName(Map<String, BeanPropertyModel.Builder> index, String name) {
            return index.computeIfAbsent(name, k -> BeanPropertyModel.builder());
        }

        private String propNameFromMethodName(String methodName) {
            return methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
        }
    }

}
