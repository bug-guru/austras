/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.mapper.apt;

import guru.bug.austras.apt.core.common.model.bean.BeanPropertyModel;

public class FieldMapping {
    private BeanPropertyModel sourceField;
    private BeanPropertyModel targetField;

    public BeanPropertyModel getSourceField() {
        return sourceField;
    }

    public void setSourceField(BeanPropertyModel sourceField) {
        this.sourceField = sourceField;
    }

    public BeanPropertyModel getTargetField() {
        return targetField;
    }

    public void setTargetField(BeanPropertyModel targetField) {
        this.targetField = targetField;
    }
}
