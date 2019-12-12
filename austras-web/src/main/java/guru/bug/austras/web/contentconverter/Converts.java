package guru.bug.austras.web.contentconverter;

import guru.bug.austras.core.Qualifier;
import guru.bug.austras.core.QualifierProperty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Qualifier(name = "Converts", properties = @QualifierProperty(name = "type"))
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE})
public @interface Converts {
    String type();
}
