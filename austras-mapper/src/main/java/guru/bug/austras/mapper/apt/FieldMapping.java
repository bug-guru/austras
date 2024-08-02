/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.mapper.apt;

import guru.bug.austras.apt.core.common.model.bean.BeanPropertyModel;

public class FieldMapping {
    private final BeanPropertyModel sourceField;
    private final BeanPropertyModel targetField;
    private final FieldMapperDependency mapperDep;

    public FieldMapping(BeanPropertyModel sourceField, BeanPropertyModel targetField, FieldMapperDependency mapperDep) {
        this.sourceField = sourceField;
        this.targetField = targetField;
        this.mapperDep = mapperDep;
    }

    public static FieldMapping of(BeanPropertyModel sourceField, BeanPropertyModel targetField, FieldMapperDependency mapperDep) {
        return new FieldMapping(sourceField, targetField, mapperDep);
    }

    public BeanPropertyModel getSourceField() {
        return sourceField;
    }

    public BeanPropertyModel getTargetField() {
        return targetField;
    }

    public FieldMapperDependency getMapperDep() {
        return mapperDep;
    }
}
