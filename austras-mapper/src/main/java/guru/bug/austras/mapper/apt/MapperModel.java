/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.mapper.apt;

import guru.bug.austras.apt.core.common.model.QualifierSetModel;
import guru.bug.austras.apt.core.common.model.bean.BeanModel;

import java.util.List;
import java.util.Objects;

public class MapperModel {
    private BeanModel source;
    private BeanModel target;
    private List<FieldMapping> mappings;
    private QualifierSetModel qualifiers;

    public BeanModel getSource() {
        return source;
    }

    public void setSource(BeanModel source) {
        this.source = source;
    }

    public BeanModel getTarget() {
        return target;
    }

    public void setTarget(BeanModel target) {
        this.target = target;
    }

    public List<FieldMapping> getMappings() {
        return mappings;
    }

    public void setMappings(List<FieldMapping> mappings) {
        this.mappings = mappings;
    }

    public void setQualifiers(QualifierSetModel qualifiers) {
        this.qualifiers = qualifiers;
    }

    public QualifierSetModel getQualifiers() {
        return qualifiers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapperModel that = (MapperModel) o;
        return Objects.equals(source, that.source) &&
               Objects.equals(target, that.target) &&
               Objects.equals(mappings, that.mappings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target, mappings);
    }
}
