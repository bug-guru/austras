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
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
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

    private static List<Property> collectProps(DeclaredType type) {
        var index = new HashMap<String, Property>();
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
    private static class PropExtractor extends SimpleElementVisitor9<Property, Void> {
        @Override
        public Property visitVariable(VariableElement e, Void aVoid) {
            if (e.getKind() != ElementKind.FIELD) {
                return null;
            }
            var prop = new Property();
            prop.name = e.getSimpleName().toString();
            prop.field = e;
            prop.type = e.asType();
            return prop;
        }

        @Override
        public Property visitExecutable(ExecutableElement e, Void aVoid) {
            if (e.getKind() != ElementKind.METHOD) {
                return null;
            }
            var prop = new Property();
            var mname = e.getSimpleName().toString();
            var isVoid = e.getReturnType().getKind() == TypeKind.VOID;
            var params = e.getParameters();
            if (mname.startsWith("get") && params.isEmpty() && !isVoid) {
                prop.getter = e;
                prop.type = e.getReturnType();
            } else if (mname.startsWith("set") && params.size() == 1 && isVoid) {
                prop.setter = e;
                prop.type = params.get(0).asType();
            } else {
                return null;
            }
            prop.name = mname.substring(3, 4).toLowerCase() + mname.substring(4);
            return prop;
        }
    }


    private static class PropertiesIndex {
        private final Map<String, BeanPropertyModel.Builder> index;


    }
}
