package guru.bug.austras.web.apt.model;

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
