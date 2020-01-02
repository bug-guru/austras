/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.mapper.generator;

public enum Numerics {
    BYTE("Byte", "byte"),
    SHORT("Short", "short"),
    INT("Integer", "int"),
    LONG("Long", "long"),
    FLOAT("Float", "float"),
    DOUBLE("Double", "double");


    private final String boxed;
    private final String primitive;

    Numerics(String boxed, String primitive) {
        this.boxed = boxed;
        this.primitive = primitive;
    }

    public String getBoxed() {
        return boxed;
    }

    public String getPrimitive() {
        return primitive;
    }
}
