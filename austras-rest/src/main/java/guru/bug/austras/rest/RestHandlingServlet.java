/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.rest;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.rest.errors.HttpException;
import guru.bug.austras.rest.errors.MethodNotAllowedException;
import guru.bug.austras.rest.errors.MultipleChoicesException;
import guru.bug.austras.rest.errors.NotFoundException;
import guru.bug.austras.startup.StartupService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Default
public class RestHandlingServlet implements StartupService, Servlet {
    private static final Logger log = LoggerFactory.getLogger(RestHandlingServlet.class);
    private static final String ACCEPT_HEADER = "Accept";
    private static final List<MediaType> WILDCARD_TYPE = List.of(MediaType.WILDCARD_TYPE);
    private final Collection<? extends EndpointHandler> endpoints;
    private ServletConfig config;

    public RestHandlingServlet(Collection<? extends EndpointHandler> endpoints) {
        this.endpoints = endpoints;
    }

    @Override
    public void initialize(ServletContext ctx) {

        var registration = ctx.addServlet(getClass().getName(), this);
        registration.setLoadOnStartup(0);
        registration.addMapping("/api/*");

        if (endpoints.isEmpty()) {
            log.warn("There are no endpoints found in the application");
        } else {
            for (var handler : endpoints) {
                log.info("Registered {}", handler);
            }
        }
    }

    @Override
    public void destroy(ServletContext ctx) {
    }


    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        if (!(req instanceof HttpServletRequest request &&
                res instanceof HttpServletResponse response)) {
            throw new ServletException("Not HTTP");
        }
        try {
            var target = request.getPathInfo();
            var pathItems = PathSplitter.split(Function.identity(), target);
            var handlerHolder = findHandler(request, pathItems, target);
            handlerHolder.handle(request, response);
        } catch (HttpException e) {
            log.error("Handling HTTP Exception", e);
            sendError(response, e.getStatusCode(), e.getHttpMessage());
        } catch (Exception e) {
            log.error("Handling unexpected exception", e);
            sendError(response, 500, "Unexpected server error");
        }
    }

    private void sendError(HttpServletResponse response, int code, String message) {
        if (response.isCommitted()) {
            log.error("Cannot send error {} {} - response is already committed", code, message);
        } else try {
            response.reset();
            response.setStatus(code);
            response.setContentType(MediaType.TEXT_PLAIN);
            try (var out = response.getWriter()) {
                out.print(code);
                out.print(" - ");
                out.print(message);
            }
        } catch (Exception e) {
            log.error("Cannot send error {} {} - unexpected exception", code, message, e);
        }
    }

    List<MediaType> getAcceptTypes(HttpServletRequest request) {
        var types = request.getHeaders(ACCEPT_HEADER);
        if (types.hasMoreElements()) {
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(types.asIterator(), Spliterator.ORDERED), false)
                    .flatMap(s -> Stream.of(s.split(",")))
                    .map(MediaType::valueOf)
                    .collect(Collectors.toList());
        } else {
            return WILDCARD_TYPE;
        }
    }

    EndpointHandlerHolder findHandler(HttpServletRequest request, List<String> pathItems, String target) throws HttpException {
        var method = request.getMethod().toUpperCase();
        var contentType = Optional.ofNullable(request.getContentType()).map(MediaType::valueOf).orElse(null);
        var acceptTypes = getAcceptTypes(request);
        log.debug("Searching a handler. Method: {}; contentType: {}; accepts types: {}", method, contentType, acceptTypes);

        var byPathFilter = new Filter(c -> byPath(c, pathItems, target));
        var byMethodFilter = new Filter(c -> byMethod(c, method));

        var candidates = endpoints.stream()
                .map(EndpointHandlerHolder::new)
                .filter(byPathFilter)
                .filter(byMethodFilter)
                .collect(Collectors.toList());
        if (candidates.size() > 1) {
            log.error("More than one candidate found {}", candidates);
            throw new MultipleChoicesException();
        }
        if (candidates.isEmpty()) {
            byPathFilter.throwIfNotPassed(() -> {
                log.error("No handler found for path {}", pathItems);
                throw new NotFoundException();
            });
            byMethodFilter.throwIfNotPassed(() -> {
                log.error("No handler found for HTTP method {}", method);
                throw new MethodNotAllowedException(method);
            });
        }
        var result = candidates.get(0);
        log.debug("Handler found: {}", result.handler);
        return result;
    }

    private boolean byPath(EndpointHandlerHolder candidate, List<String> requestPathItems, String requestPath) {
        if (candidate.handler.getPathItems().size() != requestPathItems.size()) {
            log.trace("Testing candidate: {}; path: {}}; passed: false", candidate.handler, requestPath);
            return false;
        }
        Iterator<PathItem> resPathIterator = candidate.handler.getPathItems().iterator();
        Iterator<String> requestPathIterator = requestPathItems.iterator();
        while (resPathIterator.hasNext()) {
            var resPathItem = resPathIterator.next();
            var requestPathItem = requestPathIterator.next();
            if (!resPathItem.canAccept(requestPathItem)) {
                log.trace("Testing candidate: {}; path: {}}; passed: false", candidate.handler, requestPath);
                return false;
            }
            var key = resPathItem.key();
            if (key != null) {
                candidate.putPathParam(key, requestPathItem);
            }
        }
        log.trace("Testing candidate: {}; path: {}}; passed: true", candidate.handler, requestPath);
        return true;
    }

    private boolean byMethod(EndpointHandlerHolder candidate, String method) {
        var result = candidate.handler.getMethod().equals(method);
        log.trace("Testing candidate: {}; Method: {}; passed: {}", candidate.handler, method, result);
        return result;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.config = config;
    }

    @Override
    public ServletConfig getServletConfig() {
        return config;
    }

    @Override
    public String getServletInfo() {
        return "austras-rest servlet";
    }

    @Override
    public void destroy() {

    }


    private interface HttpExceptionProducer {
        void run() throws HttpException;
    }

    private static class Filter implements Predicate<EndpointHandlerHolder> {
        final Predicate<EndpointHandlerHolder> predicate;
        boolean passed;

        Filter(Predicate<EndpointHandlerHolder> predicate) {
            this.predicate = predicate;
        }

        @Override
        public boolean test(EndpointHandlerHolder h) {
            var result = predicate.test(h);
            if (result) {
                passed = true;
            }
            return result;
        }

        void throwIfNotPassed(HttpExceptionProducer exceptionProducer) throws HttpException {
            if (!passed) {
                exceptionProducer.run();
            }
        }
    }

    static class EndpointHandlerHolder {
        final EndpointHandler handler;
        Map<String, String> pathParams;

        EndpointHandlerHolder(EndpointHandler handler) {
            this.handler = handler;
        }

        void putPathParam(String key, String value) {
            if (pathParams == null) {
                pathParams = new HashMap<>();
            }
            pathParams.put(key, value);
        }

        @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
        void handle(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, HttpException {
            handler.handle(request, pathParams, response);
        }
    }

}
