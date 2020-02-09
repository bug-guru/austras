/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.rest;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MediaType {
    public static final String APPLICATION_JSON = "application/json";
    public static final String TEXT_PLAIN = "text/plain";
    public static final String WILDCARD = "*/*";
    public static final MediaType APPLICATION_JSON_TYPE = valueOf(APPLICATION_JSON);
    public static final MediaType TEXT_PLAIN_TYPE = valueOf(TEXT_PLAIN);
    public static final MediaType WILDCARD_TYPE = valueOf(WILDCARD);
    public static final List<MediaType> WILDCARD_LIST = Collections.singletonList(WILDCARD_TYPE);
    private final String type;
    private final String subtype;
    private final String full;

    public MediaType(String type, String subtype) {
        this.type = type.toLowerCase().intern();
        this.subtype = subtype.toLowerCase().intern();
        this.full = (type + "/" + subtype).intern();
    }

    public static MediaType valueOf(String mediaType) {
        var mt = mediaType.strip();
        if (mt.isBlank()) {
            throw new IllegalArgumentException("mediaType cannot be blank");
        }
        var paramIdx = mt.indexOf(';');
        if (paramIdx != -1) { // TODO Deal with parameters
            mt = mt.substring(0, paramIdx);
        }
        if ("*".equals(mt)) {
            return MediaType.WILDCARD_TYPE;
        }
        var sepIdx = mt.indexOf('/');
        if (sepIdx == -1) {
            throw new IllegalArgumentException("unsupported mediaType: " + mediaType);
        }
        var type = mt.substring(0, sepIdx).strip();
        var subtype = mt.substring(sepIdx + 1).strip();
        if (type.isBlank() || subtype.isBlank()) {
            throw new IllegalArgumentException("unsupported mediaType: " + mediaType);
        }
        return new MediaType(type, subtype);
    }

    public String getType() {
        return type;
    }

    public String getSubtype() {
        return subtype;
    }

    public boolean isWildcardType() {
        return "*".equals(type);
    }

    public boolean isWildcardSubtype() {
        return "*".equals(subtype);
    }

    public boolean isCompatible(MediaType other) {
        if (isWildcardType() || other.isWildcardType()) {
            return true;
        }
        if (!type.equals(other.type)) {
            return false;
        }
        if (isWildcardSubtype() || other.isWildcardSubtype()) {
            return true;
        }
        return subtype.equals(other.subtype);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MediaType mediaType = (MediaType) o;
        return full.equals(mediaType.full);
    }

    @Override
    public int hashCode() {
        return Objects.hash(full);
    }

    @Override
    public String toString() {
        return full;
    }
}
