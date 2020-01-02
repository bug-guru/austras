/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.apt.core.common.model.bean;

import javax.lang.model.element.TypeElement;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BeanModel {
    private final TypeElement dataClassElement;
    private final Map<String, BeanPropertyModel> properties;

    public BeanModel(TypeElement dataClassElement, Set<BeanPropertyModel> properties) {
        this.dataClassElement = dataClassElement;
        this.properties = properties.stream()
                .collect(Collectors.toMap(BeanPropertyModel::getName, Function.identity()));
    }
}
