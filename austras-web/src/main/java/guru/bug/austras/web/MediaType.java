package guru.bug.austras.web;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MediaType {
    public static final String APPLICATION_JSON = "application/json";
    public static final String WILDCARD = "*/*";
    private static final Pattern MEDIA_TYPE_REGEX = Pattern.compile("(\\w+)/([^;]+)(;\\w+)?");
    public static final MediaType APPLICATION_JSON_TYPE = valueOf(APPLICATION_JSON);
    public static final MediaType WILDCARD_TYPE = valueOf(WILDCARD);
    public static final List<MediaType> WILDCARD_LIST = Collections.singletonList(WILDCARD_TYPE);
    private final String type;
    private final String subtype;

    public MediaType(String type, String subtype) {
        this.type = type.toLowerCase();
        this.subtype = subtype.toLowerCase();
    }

    public static MediaType valueOf(String mediaType) {
        Matcher matcher = MEDIA_TYPE_REGEX.matcher(mediaType);
        if (matcher.matches()) {
            var type = matcher.group(1).trim();
            var subtype = matcher.group(2).trim();
            return new MediaType(type, subtype);
        } else {
            throw new IllegalArgumentException("mediaType");
        }
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
        return type.equals(mediaType.type) &&
                subtype.equals(mediaType.subtype);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, subtype);
    }

    @Override
    public String toString() {
        return type + "/" + subtype;
    }
}
