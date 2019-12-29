package guru.bug.austras.test.rest;

import guru.bug.austras.convert.content.ContentConverter;
import guru.bug.austras.core.Selector;
import guru.bug.austras.core.qualifiers.Any;
import guru.bug.austras.web.EndpointHandler;
import guru.bug.austras.web.PathItem;
import guru.bug.austras.web.errors.HttpException;
import guru.bug.austras.web.errors.NotAcceptableException;
import guru.bug.austras.web.errors.NotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TestControllerHandler extends EndpointHandler {
    private final Selector<ContentConverter<MyDataObject>> resultConverterSelector;

    public TestControllerHandler(@Any Selector<ContentConverter<MyDataObject>> resultConverterSelector) {
        super("POST",
                List.of(
                        PathItem.matching("test"),
                        PathItem.param("group")
                )
        );
        this.resultConverterSelector = resultConverterSelector;
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void handle(HttpServletRequest request, Map<String, String> pathParams, HttpServletResponse response) throws IOException, ServletException, HttpException {
        var selectedResponseConverter =
                selectResponseContentType(resultConverterSelector, request)
                        .orElseThrow(NotAcceptableException::new);
        var group = pathParams.get("group");
        if ("a".equals(group)) {
            throw new NotFoundException();
        }
        var result = new MyDataObject();
        result.setGroup(group);
        response.setContentType(selectedResponseConverter.getMediaType().toString());
        try (var out = response.getWriter()) {
            response.setCharacterEncoding("UTF-8"); // TODO what to do with encoding?
            selectedResponseConverter.getConverter().write(result, out);
        }
    }


}
