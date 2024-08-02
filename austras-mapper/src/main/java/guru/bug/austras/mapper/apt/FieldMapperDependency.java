/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.mapper.apt;

import javax.lang.model.type.DeclaredType;

public class FieldMapperDependency {
    private final String name;
    private final DeclaredType type;

    public FieldMapperDependency(String name, DeclaredType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public DeclaredType getType() {
        return type;
    }
}
