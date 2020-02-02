/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.rest.apt.model;

import guru.bug.austras.apt.core.common.model.DependencyModel;

import java.util.Objects;

public class DependencyRef {
    private final String varName;
    private final DependencyModel dependencyModel;

    public DependencyRef(String varName, DependencyModel dependencyModel) {
        this.varName = varName;
        this.dependencyModel = dependencyModel;
    }

    public String getVarName() {
        return varName;
    }

    public DependencyModel getDependencyModel() {
        return dependencyModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DependencyRef that = (DependencyRef) o;
        return varName.equals(that.varName) &&
                dependencyModel.equals(that.dependencyModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(varName, dependencyModel);
    }
}
