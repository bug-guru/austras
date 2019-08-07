package guru.bug.austras.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Repeatable(Qualifiers.class)
public @interface Qualifier {
    String name();

    QualifierProperty[] properties() default {};
}
