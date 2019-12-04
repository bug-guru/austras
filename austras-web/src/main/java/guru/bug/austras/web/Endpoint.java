package guru.bug.austras.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface Endpoint {
    String method();

    String path();

    String[] accept() default {MediaType.WILDCARD};

    String[] produce() default {MediaType.WILDCARD};
}
