package guru.bug.austras.web;

import guru.bug.austras.core.Component;
import guru.bug.austras.startup.StartupService;
import guru.bug.austras.web.errors.*;
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
import java.util.stream.StreamSupport;

@Component
public class RestServer implements StartupService {
    private static final Logger log = LoggerFactory.getLogger(RestServer.class);
    private static final String ACCEPT_HEADER = "Accept";
    private final List<EndpointHandler> endpoints;
    private final JettyHandler jettyHandler = new JettyHandler();
    private Server server;

    public RestServer(Collection<? extends EndpointHandler> endpoints) {
        this.endpoints = endpoints == null ? List.of() : List.copyOf(endpoints);
    }

    @Override
    public void initialize() {
        this.server = new Server(8080);

        server.setHandler(jettyHandler);

        try {
            server.start();
        } catch (Exception e) {
            throw new IllegalStateException(e);
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

        void throwIfNotPassed(Runnable exceptionProducer) {
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

        void handle(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            handler.handle(request, pathParams, response);
        }
    }

    private class JettyHandler extends AbstractHandler {

        @Override
        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            try {
                var pathItems = PathSplitter.split(Function.identity(), target);
                var handlerHolder = findHandler(request, pathItems);
                handlerHolder.handle(request, response);
            } catch (AustrasHttpException e) {
                log.error("Handling exception", e);
                response.setStatus(e.getStatusCode());
                response.sendError(e.getStatusCode(), e.getMessage());
            }
        }

        List<MediaType> getAcceptTypes(HttpServletRequest request) {
            var types = request.getHeaders(ACCEPT_HEADER);
            if (!types.hasMoreElements()) {
                return List.of(MediaType.WILDCARD_TYPE);
            } else {
                return StreamSupport.stream(Spliterators.spliteratorUnknownSize(types.asIterator(), Spliterator.ORDERED), false)
                        .map(MediaType::valueOf)
                        .collect(Collectors.toList());
            }
        }

        EndpointHandlerHolder findHandler(HttpServletRequest request, List<String> pathItems) {
            var method = request.getMethod().toUpperCase();
            var contentType = request.getContentType();
            var acceptTypes = getAcceptTypes(request);

            var byMethodFilter = new Filter(c -> byMethod(c, method));
            var byContentFilter = new Filter(c -> byContentType(c, contentType));
            var byPathFilter = new Filter(c -> byPath(c, pathItems));
            var byAcceptFilter = new Filter(c -> byAccept(c, acceptTypes));

            var candidates = endpoints.stream()
                    .map(EndpointHandlerHolder::new)
                    .filter(byMethodFilter)
                    .filter(byContentFilter)
                    .filter(byPathFilter)
                    .filter(byAcceptFilter)
                    .collect(Collectors.toList());
            if (candidates.size() > 1) {
                throw new MultipleChoicesException();
            }
            if (candidates.isEmpty()) {
                byMethodFilter.throwIfNotPassed(() -> {
                    throw new MethodNotAllowedException();
                });
                byContentFilter.throwIfNotPassed(() -> {
                    throw new UnsupportedMediaTypeException();
                });
                byPathFilter.throwIfNotPassed(() -> {
                    throw new NotFoundException();
                });
                byAcceptFilter.throwIfNotPassed(() -> {
                    throw new NotAcceptableException();
                });
            }
            return candidates.get(0);
        }

        private boolean byPath(EndpointHandlerHolder candidate, List<String> requestPath) {
            if (candidate.handler.getPath().size() != requestPath.size()) {
                return false;
            }
            Iterator<PathItem> resPathIterator = candidate.handler.getPath().iterator();
            Iterator<String> requestPathIterator = requestPath.iterator();
            while (resPathIterator.hasNext()) {
                var resPathItem = resPathIterator.next();
                var requestPathItem = requestPathIterator.next();
                if (!resPathItem.canAccept(requestPathItem)) {
                    return false;
                }
                var key = resPathItem.key();
                if (key != null) {
                    candidate.putPathParam(key, requestPathItem);
                }
            }
            return true;
        }

        private boolean byMethod(EndpointHandlerHolder candidate, String method) {
            return candidate.handler.getMethod().equals(method);
        }

        private boolean byContentType(EndpointHandlerHolder candidate, String contentType) {
            if (contentType == null) {
                return true;
            }
            var targetMT = MediaType.valueOf(contentType);
            return acceptTypes(targetMT, candidate.handler.getConsumedTypes());
        }

        private boolean byAccept(EndpointHandlerHolder candidate, List<MediaType> acceptTypes) {
            return acceptTypes(acceptTypes, candidate.handler.getProducedTypes());
        }

        private boolean acceptTypes(MediaType requestedType, List<MediaType> targetTypes) {
            return targetTypes.stream().anyMatch(requestedType::isCompatible);
        }

        private boolean acceptTypes(List<MediaType> requestedTypes, List<MediaType> targetTypes) {
            return requestedTypes.stream().anyMatch(rmt -> acceptTypes(rmt, targetTypes));
        }

    }
}
