package guru.bug.austras.test.rest;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
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
    private final JsonConverter<MyDataObject> converter;

    public TestControllerHandler(JsonConverter<MyDataObject> converter) {
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
        this.converter = converter;
    }

    @Override
    public void handle(HttpServletRequest request, Map<String, String> pathParams, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON);
        var result = new MyDataObject();
        result.setGroup(pathParams.get("group"));
        try (var out = response.getWriter()) {
            JsonValueWriter w = JsonValueWriter.newInstance(out);
            converter.toJson(result, w);
        }
    }
}
