package guru.bug.austras.core.qualifiers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
public @interface Qualifiers {
    MatchType match() default MatchType.EXACT;

    Qualifier[] value();
}
