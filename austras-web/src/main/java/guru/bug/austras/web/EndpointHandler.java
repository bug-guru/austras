package guru.bug.austras.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class EndpointHandler {
    private final List<PathItem> path;
    private final String method;
    private final List<MediaType> consumedTypes;
    private final List<MediaType> producedTypes;

    protected EndpointHandler(String method, List<PathItem> path, List<MediaType> consumedTypes, List<MediaType> producedTypes) {
        this.path = path;
        this.method = Objects.requireNonNull(method).toUpperCase().intern();
        this.consumedTypes = consumedTypes;
        this.producedTypes = producedTypes;
    }

    public final String getMethod() {
        return method;
    }

    public final List<PathItem> getPath() {
        return path;
    }

    public final List<MediaType> getConsumedTypes() {
        return consumedTypes;
    }

    public final List<MediaType> getProducedTypes() {
        return producedTypes;
    }

    public abstract void handle(HttpServletRequest request, Map<String, String> pathParams, HttpServletResponse response) throws IOException, ServletException;
}
