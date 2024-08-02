/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.test.rest;

import guru.bug.austras.convert.ContentConverter;
import guru.bug.austras.core.Selector;
import guru.bug.austras.core.qualifiers.Any;
import guru.bug.austras.rest.EndpointHandler;
import guru.bug.austras.rest.PathItem;
import guru.bug.austras.rest.errors.HttpException;
import guru.bug.austras.rest.errors.NotAcceptableException;
import guru.bug.austras.rest.errors.NotFoundException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TestControllerHandler extends EndpointHandler {
    private final Selector<ContentConverter<MyDataObject>> resultConverterSelector;

    public TestControllerHandler(@Any Selector<ContentConverter<MyDataObject>> resultConverterSelector) {
        super("POST",
                "/test/group",
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
