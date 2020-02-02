/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.rest.apt.model;

public class PathParamMethodParam implements MethodParam {
    private final String paramName;
    private final DependencyRef converter;

    public PathParamMethodParam(String paramName, DependencyRef converter) {
        this.paramName = paramName;
        this.converter = converter;
    }

    @Override
    public String expresion() {
        var result = "pathParams.get(\"" + paramName + "\")";
        if (converter != null) {
            result = converter.getVarName() + ".fromString(" + result + ")";
        }
        return result;
    }
}
