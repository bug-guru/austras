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
}
