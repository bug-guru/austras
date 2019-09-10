package guru.bug.austras.web;

import java.util.Objects;

public class ResourcePathSimpleItem implements ResourcePathItem {
    private final String value;

    public ResourcePathSimpleItem(String value) {
        this.value = value;
    }

    @Override
    public boolean canAccept(String value) {
        return Objects.equals(value, this.value);
    }
}
