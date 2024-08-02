/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.apt.core.common.model;

import javax.lang.model.type.TypeMirror;
import java.util.Objects;

public class ComponentRef {
    private final TypeMirror type;
    private final QualifierSetModel qualifiers;

    private ComponentRef(TypeMirror type, QualifierSetModel qualifiers) {
        this.type = Objects.requireNonNull(type);
        this.qualifiers = Objects.requireNonNull(qualifiers);
    }

    public static ComponentRef of(TypeMirror type, QualifierSetModel qualifiers) {
        return new ComponentRef(type, qualifiers);
    }

    public TypeMirror getType() {
        return type;
    }

    public QualifierSetModel getQualifiers() {
        return qualifiers;
    }
}
