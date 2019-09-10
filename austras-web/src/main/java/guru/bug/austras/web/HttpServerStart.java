package guru.bug.austras.web;

import guru.bug.austras.core.Component;
import guru.bug.austras.startup.StartupService;
import guru.bug.austras.web.errors.*;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class HttpServerStart implements StartupService {
    public static final String ACCEPT_HEADER = "Accept";
    private final List<EndpointHandler> endpoints;
    private final JettyHandler jettyHandler = new JettyHandler();
    private Server server;

    public HttpServerStart(Collection<EndpointHandler> endpoints) {
        this.endpoints = endpoints == null ? List.of() : List.copyOf(endpoints);
    }

    @Override
    public void initialize() {
        this.server = new Server(8080);

        server.setHandler(jettyHandler);

        try {
            server.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {
        try {
            server.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private class JettyHandler extends AbstractHandler {

        @Override
        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            try {
                var pathItems = new PathSplitter<>(Function.identity(), List.of(target)).getItems();
                var handler =
            } catch {
                log.log(Level.WARNING, "Handling exception", e);
                response.setStatusCode(e.getStatusCode());
                response.setReasonPhrase(e.getMessage());
                response.setContent(null);
            }
        }

        private List<MediaType> getAcceptTypes(HttpServletRequest request) {
            var types = request.getHeaders(ACCEPT_HEADER);
            if (!types.hasMoreElements()) {
                return List.of(MediaType.WILDCARD_TYPE);
            } else {
                return StreamSupport.stream(Spliterators.spliteratorUnknownSize(types.asIterator(), Spliterator.ORDERED), false)
                        .map(MediaType::valueOf)
                        .collect(Collectors.toList());
            }
        }

        private EndpointHandler findHandler(HttpServletRequest request, List<String> pathItems) {
            List<EndpointHandler> candidates = endpoints;
            candidates = getHandlersFilteredByPath(candidates, pathItems);
            candidates = getHandlersFilteredByMethod(candidates, request.getMethod());
            candidates = getHandlersFilteredByContentType(candidates, request.getContentType());
            candidates = getHandlersFilteredByAccept(candidates, getAcceptTypes(request));
            if (candidates.size() > 1) {
                throw new MultipleChoicesException();
            }
            if (candidates.isEmpty()) {
                throw new NotFoundException();
            }
            return candidates.get(0);
        }

        private boolean acceptedPath(List<ResourcePathItem> resPath, List<String> requestPath) {
            Iterator<ResourcePathItem> resPathIterator = resPath.iterator();
            Iterator<String> requestPathIterator = requestPath.iterator();
            while (resPathIterator.hasNext() && requestPathIterator.hasNext()) {
                var resPathItem = resPathIterator.next();
                var requestPathItem = requestPathIterator.next();
                if (!resPathItem.canAccept(requestPathItem)) {
                    return false;
                }
            }
            return !resPathIterator.hasNext() && !requestPathIterator.hasNext();
        }

        private List<EndpointHandler> getHandlersFilteredByPath(List<EndpointHandler> candidates, List<String> requestPath) {
            candidates = candidates.stream()
                    .filter(h -> h.getPath().size() == requestPath.size())
                    .filter(h -> acceptedPath(h.getPath(), requestPath))
                    .collect(Collectors.toList());
            if (candidates.isEmpty()) {
                throw new NotFoundException();
            }
            return candidates;
        }

        private List<EndpointHandler> getHandlersFilteredByMethod(List<EndpointHandler> candidates, String method) {
            candidates = candidates.stream()
                    .filter(h -> h.getMethod().equals(method))
                    .collect(Collectors.toList());
            if (candidates.isEmpty()) {
                throw new MethodNotAllowedException();
            }
            return candidates;
        }

        private boolean acceptTypes(MediaType requestedType, List<MediaType> targetTypes) {
            return targetTypes.stream().anyMatch(requestedType::isCompatible);
        }

        private List<EndpointHandler> getHandlersFilteredByContentType(List<EndpointHandler> candidates, String contentType) {
            List<EndpointHandler> result = Optional.ofNullable(contentType)
                    .map(MediaType::valueOf)
                    .map(ct -> candidates.stream()
                            .filter(h -> acceptTypes(ct, h.getConsumedTypes()))
                            .collect(Collectors.toList()))
                    .orElse(candidates);
            if (result.isEmpty()) {
                throw new UnsupportedMediaTypeException();
            }
            return result;
        }

        private List<EndpointHandler> getHandlersFilteredByAccept(List<EndpointHandler> candidates, List<MediaType> acceptTypes) {
            candidates = candidates.stream()
                    .filter(h -> acceptTypes(acceptTypes, h.getProducedTypes()))
                    .collect(Collectors.toList());
            if (candidates.isEmpty()) {
                throw new NotAcceptableException();
            }
            return candidates;
        }

        private boolean acceptTypes(List<MediaType> requestedTypes, List<MediaType> targetTypes) {
            return requestedTypes.stream().anyMatch(rmt -> acceptTypes(rmt, targetTypes));
        }

    }
}
