package guru.bug.austras.web.apt.model;

public class HeaderParamMethodParam implements MethodParam {
    private final String paramName;
    private final DependencyRef converter;

    public HeaderParamMethodParam(String paramName, DependencyRef converter) {
        this.paramName = paramName;
        this.converter = converter;
    }

    @Override
    public String expresion() {
        var result = "request.getHeader(\"" + paramName + "\")";
        if (converter != null) {
            result = converter.getVarName() + ".fromString(" + result + ")";
        }
        return result;
    }
}
