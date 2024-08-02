/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.rest;

import guru.bug.austras.convert.ContentConverter;
import guru.bug.austras.convert.Converts;
import guru.bug.austras.core.Selector;
import guru.bug.austras.rest.errors.HttpException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class EndpointHandler {
    private static final String ACCEPT_HEADER = "Accept";
    private static final List<MediaType> WILDCARD_TYPE = List.of(MediaType.WILDCARD_TYPE);
    private final List<PathItem> pathItems;
    private final String path;
    private final String method;

    protected EndpointHandler(String method, String path, List<PathItem> pathItems) {
        this.pathItems = pathItems;
        this.path = path;
        this.method = Objects.requireNonNull(method).toUpperCase().intern();
    }

    public final String getMethod() {
        return method;
    }

    public final List<PathItem> getPathItems() {
        return pathItems;
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

    private MediaType getContentType(HttpServletRequest request) {
        var strType = request.getContentType();
        if (strType == null || strType.isBlank()) {
            return null;
        }
        return MediaType.valueOf(strType);
    }

    protected final <T> Optional<ContentTypeSelector<T>> selectResponseContentType(Selector<? extends ContentConverter<T>> availableConverters, HttpServletRequest request) {
        var result = new ContentTypeSelector<T>(availableConverters);
        if (result.select(getAcceptTypes(request))) {
            return Optional.of(result);
        } else {
            return Optional.empty();
        }
    }

    protected final <T> Optional<ContentTypeSelector<T>> selectRequestContentType(Selector<? extends ContentConverter<T>> availableConverters, HttpServletRequest request) {
        var result = new ContentTypeSelector<T>(availableConverters);
        var contentType = getContentType(request);
        if (contentType == null) {
            return Optional.empty();
        } else if (result.select(List.of(contentType))) {
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
        private final Selector<? extends ContentConverter<T>> availableConverters;
        private ContentConverter<T> selectedConverter;
        private MediaType selectedMediaType;


        private ContentTypeSelector(Selector<? extends ContentConverter<T>> availableConverters) {
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
