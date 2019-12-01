package guru.bug.austras.test.rest;

import guru.bug.austras.web.EndpointHandler;
import guru.bug.austras.web.MediaType;
import guru.bug.austras.web.PathItem;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TestControllerHandler extends EndpointHandler {
    public TestControllerHandler() {
        super("GET",
                List.of(
                        PathItem.matching("test"),
                        PathItem.param("group")
                ),
                List.of(
                        MediaType.WILDCARD_TYPE
                ),
                List.of(
                        MediaType.APPLICATION_JSON_TYPE
                )
        );
    }

    @Override
    public void handle(HttpServletRequest request, Map<String, String> pathParams, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON);
        try (var out = response.getWriter()) {
            out.print("{\"group\":\"" + pathParams.get("group") + "\"}");
        }
    }
}
