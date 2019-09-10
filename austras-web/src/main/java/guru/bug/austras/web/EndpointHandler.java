package guru.bug.austras.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface EndpointHandler {
    List<ResourcePathItem> getPath();

    String getMethod();

    List<MediaType> getConsumedTypes();

    List<MediaType> getProducedTypes();

    void handle(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
