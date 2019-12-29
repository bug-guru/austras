package guru.bug.austras.web.apt.model;

public class QueryParamMethodParam implements MethodParam {
    private final String paramName;
    private final DependencyRef converter;

    public QueryParamMethodParam(String paramName, DependencyRef converter) {
        this.paramName = paramName;
        this.converter = converter;
    }

    @Override
    public String expresion() {
        var result = "request.getParameter(\"" + paramName + "\")";
        if (converter != null) {
            result = converter.getVarName() + ".fromString(" + result + ")";
        }
        return result;
    }
}
