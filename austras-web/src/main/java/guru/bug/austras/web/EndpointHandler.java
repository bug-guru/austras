package guru.bug.austras.web;

import guru.bug.austras.convert.content.ContentConverter;
import guru.bug.austras.convert.content.Converts;
import guru.bug.austras.core.Selector;
import guru.bug.austras.web.errors.HttpException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class EndpointHandler {
    private static final String ACCEPT_HEADER = "Accept";
    private static final List<MediaType> WILDCARD_TYPE = List.of(MediaType.WILDCARD_TYPE);
    private final List<PathItem> path;
    private final String method;

    protected EndpointHandler(String method, List<PathItem> path) {
        this.path = path;
        this.method = Objects.requireNonNull(method).toUpperCase().intern();
    }

    public final String getMethod() {
        return method;
    }

    public final List<PathItem> getPath() {
        return path;
    }

    protected final List<MediaType> getAcceptTypes(HttpServletRequest request) {
        var types = request.getHeaders(ACCEPT_HEADER);
        if (types.hasMoreElements()) {
            return List.copyOf(StreamSupport.stream(Spliterators.spliteratorUnknownSize(types.asIterator(), Spliterator.ORDERED), false)
                    .flatMap(s -> Stream.of(s.split(",")))
                    .map(MediaType::valueOf)
                    .collect(Collectors.toCollection(LinkedHashSet::new)));
        } else {
            return WILDCARD_TYPE;
        }
    }

    protected final <T> Optional<ContentTypeSelector<T>> selectResponseContentType(Selector<ContentConverter<T>> availableConverters, HttpServletRequest request) {
        var result = new ContentTypeSelector<T>(availableConverters);
        if (result.select(getAcceptTypes(request))) {
            return Optional.of(result);
        } else {
            return Optional.empty();
        }
    }

    public abstract void handle(HttpServletRequest request, Map<String, String> pathParams, HttpServletResponse response) throws IOException, ServletException, HttpException;

    @Override
    public String toString() {
        return "Endpoint " + method + " " + path;
    }

    protected static final class ContentTypeSelector<T> {
        private final Selector<ContentConverter<T>> availableConverters;
        private ContentConverter<T> selectedConverter;
        private MediaType selectedMediaType;


        private ContentTypeSelector(Selector<ContentConverter<T>> availableConverters) {
            this.availableConverters = availableConverters;
        }

        private boolean select(List<MediaType> acceptableMediaTypes) {
            for (var accepted : acceptableMediaTypes) {
                selectedConverter = availableConverters.any(qs -> qs.getQualifier(Converts.QUALIFIER_NAME)
                        .flatMap(q -> q.getValue(Converts.PROPERTY_TYPE))
                        .map(mt -> {
                            selectedMediaType = MediaType.valueOf(mt);
                            return selectedMediaType;
                        })
                        .filter(accepted::isCompatible)
                        .isPresent()
                ).orElse(null);
                if (selectedConverter != null) {
                    return true;
                }
            }
            return false;
        }

        public ContentConverter<T> getConverter() {
            return selectedConverter;
        }

        public MediaType getMediaType() {
            return selectedMediaType;
        }
    }
}
