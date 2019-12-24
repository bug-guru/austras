package guru.bug.austras.core.qualifiers;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Repeatable(Qualifiers.class)
public @interface Qualifier {
    String name();

    QualifierProperty[] properties() default {};
}
