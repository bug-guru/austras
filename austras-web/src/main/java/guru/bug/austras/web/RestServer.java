package guru.bug.austras.web;

import guru.bug.austras.core.Selector;
import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.startup.StartupService;
import guru.bug.austras.web.errors.HttpException;
import guru.bug.austras.web.errors.MethodNotAllowedException;
import guru.bug.austras.web.errors.MultipleChoicesException;
import guru.bug.austras.web.errors.NotFoundException;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Default
public class RestServer implements StartupService {
    private static final Logger log = LoggerFactory.getLogger(RestServer.class);
    private static final String ACCEPT_HEADER = "Accept";
    private static final List<MediaType> WILDCARD_TYPE = List.of(MediaType.WILDCARD_TYPE);
    private final Selector<? extends EndpointHandler> endpointsSelector;
    private final JettyHandler jettyHandler = new JettyHandler();
    private Server server;

    public RestServer(Selector<? extends EndpointHandler> endpointsSelector) {
        this.endpointsSelector = endpointsSelector;
    }

    @Override
    public void initialize() {

        this.server = new Server(8080);

        var endpointHandlers = this.endpointsSelector.get();
        if (endpointHandlers.isEmpty()) {
            log.warn("There are no endpoints found in the application");
        } else {
            for (var handler : endpointHandlers) {
                log.info("Registered {}", handler);
            }
        }

        server.setHandler(jettyHandler);

        try {
            server.start();
        } catch (Exception e) {
            throw new RestServerInitializationException(e);
        }
    }

    @Override
    public void destroy() {
        try {
            server.stop();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
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

    private class JettyHandler extends AbstractHandler {

        @Override
        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) {
            try {
                var pathItems = PathSplitter.split(Function.identity(), target);
                var handlerHolder = findHandler(request, pathItems);
                handlerHolder.handle(request, response);
            } catch (HttpException e) {
                log.error("Handling HTTP Exception", e);
                sendError(response, e.getStatusCode(), e.getMessage());
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
                response.sendError(code, message);
            } catch (Exception e) {
                log.error("Cannot send error " + code + " " + message + " - unexpected exception", e);
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

        EndpointHandlerHolder findHandler(HttpServletRequest request, List<String> pathItems) throws HttpException {
            var method = request.getMethod().toUpperCase();
            var contentType = Optional.ofNullable(request.getContentType()).map(MediaType::valueOf).orElse(null);
            var acceptTypes = getAcceptTypes(request);
            log.debug("Searching a handler. Method: {}; contentType: {}; accepts types: {}", method, contentType, acceptTypes);

            var byMethodFilter = new Filter(c -> byMethod(c, method));
            var byPathFilter = new Filter(c -> byPath(c, pathItems));

            var candidates = endpointsSelector.get().stream()
                    .map(EndpointHandlerHolder::new)
                    .filter(byMethodFilter)
                    .filter(byPathFilter)
                    .collect(Collectors.toList());
            if (candidates.size() > 1) {
                log.error("More than one candidate found {}", candidates);
                throw new MultipleChoicesException();
            }
            if (candidates.isEmpty()) {
                byMethodFilter.throwIfNotPassed(() -> {
                    log.error("No handler found for HTTP method {}", method);
                    throw new MethodNotAllowedException();
                });
                byPathFilter.throwIfNotPassed(() -> {
                    log.error("No handler found for path {}", pathItems);
                    throw new NotFoundException();
                });
            }
            var result = candidates.get(0);
            log.debug("Handler found: {}", result.handler);
            return result;
        }

        private boolean byPath(EndpointHandlerHolder candidate, List<String> requestPath) {
            if (candidate.handler.getPath().size() != requestPath.size()) {
                log.trace("Testing candidate: {}; path: {}}; passed: false", candidate.handler, requestPath);
                return false;
            }
            Iterator<PathItem> resPathIterator = candidate.handler.getPath().iterator();
            Iterator<String> requestPathIterator = requestPath.iterator();
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

    }
}
