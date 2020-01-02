/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.apt.core.common.model.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.SimpleElementVisitor9;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BeanModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanModel.class);
    private static final PropExtractor propExtractor = new PropExtractor();
    private final TypeElement dataClassElement;
    private final Map<String, BeanPropertyModel> properties;

    public BeanModel(TypeElement dataClassElement, Set<BeanPropertyModel> properties) {
        this.dataClassElement = dataClassElement;
        this.properties = properties.stream()
                .collect(Collectors.toMap(BeanPropertyModel::getName, Function.identity()));
    }

    private static List<BeanPropertyModel> collectProps(DeclaredType type) {
        var index = new HashMap<String, BeanPropertyModel.Builder>();
        for (var e : type.asElement().getEnclosedElements()) {
            var prop = e.accept(propExtractor, null);
            if (prop == null) {
                continue;
            }
            try {
                index.compute(prop.name, (k, v) -> merge(prop, v));
            } catch (Exception ex) {
                throw new IllegalArgumentException("Collecting props error " + type, ex);
            }
        }
        return index.values().stream()
                .filter(BeanModel::isValid)
                .map(p -> {

                })
                .collect(Collectors.toList());
    }

    private static boolean isValid(Property property) {
        if (property.type == null || property.name == null || property.name.isBlank()) {
            throw new IllegalArgumentException("invalid property " + property);
        }
        return property.setter != null && property.getter != null;
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
            var prop = index.computeIfAbsent(name, k -> BeanPropertyModel.builder());
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
            var mname = e.getSimpleName().toString();
            var isVoid = e.getReturnType().getKind() == TypeKind.VOID;
            var params = e.getParameters();
            if (mname.startsWith("get") && params.isEmpty() && !isVoid) {
                var name = mname.substring(3, 4).toLowerCase() + mname.substring(4);
                var prop = index.computeIfAbsent(name, k -> BeanPropertyModel.builder());
                prop.getter(name, e.getReturnType(), e);
            } else if (mname.startsWith("set") && params.size() == 1 && isVoid) {
                var name = mname.substring(3, 4).toLowerCase() + mname.substring(4);
                var prop = index.computeIfAbsent(name, k -> BeanPropertyModel.builder());
                prop.getter(name, params.get(0).asType(), e);
            }
            return null;
        }
    }

}
