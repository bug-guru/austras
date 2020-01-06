/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.mapper.apt;

import guru.bug.austras.apt.core.common.model.bean.BeanPropertyModel;

public class FieldMapping {
    private final BeanPropertyModel sourceField;
    private final BeanPropertyModel targetField;

    public FieldMapping(BeanPropertyModel sourceField, BeanPropertyModel targetField) {
        this.sourceField = sourceField;
        this.targetField = targetField;
    }

    public static FieldMapping of(BeanPropertyModel sourceField, BeanPropertyModel targetField) {
        return new FieldMapping(sourceField, targetField);
    }

    public BeanPropertyModel getSourceField() {
        return sourceField;
    }

    public BeanPropertyModel getTargetField() {
        return targetField;
    }

}
