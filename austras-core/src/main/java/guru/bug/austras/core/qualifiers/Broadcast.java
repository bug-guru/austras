package guru.bug.austras.core.qualifiers;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Qualifier(name = Broadcast.QUALIFIER_NAME)
public @interface Broadcast {
    String QUALIFIER_NAME = "austras.broadcast";
}
