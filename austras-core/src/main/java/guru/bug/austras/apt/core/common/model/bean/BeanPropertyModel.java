/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.apt.core.common.model.bean;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.Objects;

public class BeanPropertyModel {
    private final String name;
    private final TypeMirror type;
    private final VariableElement field;
    private final ExecutableElement setter;
    private final ExecutableElement getter;

    public BeanPropertyModel(String name, TypeMirror type, VariableElement field, ExecutableElement setter, ExecutableElement getter) {
        this.name = name;
        this.type = type;
        this.field = field;
        this.setter = setter;
        this.getter = getter;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public TypeMirror getType() {
        return type;
    }

    public VariableElement getField() {
        return field;
    }

    public ExecutableElement getSetter() {
        return setter;
    }

    public ExecutableElement getGetter() {
        return getter;
    }

    public static class Builder {
        private String name;
        private TypeMirror type;
        private VariableElement field;
        private ExecutableElement setter;
        private ExecutableElement getter;

        public Builder field(String name, TypeMirror type, VariableElement field) {
            if (this.field != null) {
                throw new IllegalArgumentException("Field " + field + " is already set");
            }
            validateAndSetNameAndType(name, type);
            this.field = Objects.requireNonNull(field);
            return this;
        }

        public Builder setter(String name, TypeMirror type, ExecutableElement setter) {
            if (this.setter != null) {
                throw new IllegalArgumentException("Setter " + setter + " is already set");
            }
            validateAndSetNameAndType(name, type);
            this.setter = Objects.requireNonNull(setter);
            return this;
        }

        public Builder getter(String name, TypeMirror type, ExecutableElement getter) {
            if (this.getter != null) {
                throw new IllegalArgumentException("Getter " + getter + " is already set");
            }
            validateAndSetNameAndType(name, type);
            this.getter = Objects.requireNonNull(getter);
            return this;
        }

        private void validateAndSetNameAndType(String name, TypeMirror type) {
            if (this.name == null) {
                this.name = name;
                this.type = type;
            } else {
                if (!this.name.equals(name)) {
                    throw new IllegalArgumentException("different name " + this.name + " and " + name);
                }
                if (!this.type.equals(type)) {
                    throw new IllegalArgumentException("different type " + this.type + " and " + type);
                }
            }
        }
    }
}
