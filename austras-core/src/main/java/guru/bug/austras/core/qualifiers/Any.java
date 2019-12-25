package guru.bug.austras.core.qualifiers;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Qualifier(name = Any.QUALIFIER_NAME)
public @interface Any {
    String QUALIFIER_NAME = "austras.any";
}
