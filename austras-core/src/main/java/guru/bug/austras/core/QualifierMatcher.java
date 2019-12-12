package guru.bug.austras.core;

import java.util.Map;

public class QualifierMatcher {
    private
    private QualifierMatcher() {

    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private Map<String, String> properties;

        private Builder() {
        }

        public Builder name() {

        }

    }

}
