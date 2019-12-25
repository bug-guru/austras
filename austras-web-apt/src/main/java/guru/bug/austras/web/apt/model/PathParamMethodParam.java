package guru.bug.austras.web.apt.model;

import guru.bug.austras.apt.core.common.model.DependencyModel;

public class PathParamMethodParam implements MethodParam {
    private final String paramName;
    private final DependencyModel converter;

    public PathParamMethodParam(String paramName, DependencyModel converter) {
        this.paramName = paramName;
        this.converter = converter;
    }

    @Override
    public String expresion() {
        var result = "_pathParams.get(\"" + paramName + "\")";
//        if (converter != null) {
//            result = converter.getName() + ".fromString(" + result + ")";
//        }
        return result;
    }
}
