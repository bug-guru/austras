package guru.bug.austras.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface QualifierProperty {
    String name();

    String value() default "";
}
