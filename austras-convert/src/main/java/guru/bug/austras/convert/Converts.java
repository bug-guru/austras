package guru.bug.austras.convert;

import guru.bug.austras.core.qualifiers.Qualifier;
import guru.bug.austras.core.qualifiers.QualifierProperty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Qualifier(name = Converts.QUALIFIER_NAME, properties = @QualifierProperty(name = Converts.PROPERTY_TYPE))
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.PARAMETER})
public @interface Converts {
    String QUALIFIER_NAME = "austras.converts";
    String PROPERTY_TYPE = "type";

    String type();
}
