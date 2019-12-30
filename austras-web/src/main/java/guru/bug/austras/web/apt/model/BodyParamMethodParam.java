package guru.bug.austras.web.apt.model;

public class BodyParamMethodParam implements MethodParam {
    private final String paramName;
    private final DependencyRef converter;

    public BodyParamMethodParam(String paramName, DependencyRef converter) {
        this.paramName = paramName;
        this.converter = converter;
    }

    @Override
    public String expresion() {
        return "selectedRequestConverter.getConverter().read(request.getReader())";
    }
}
