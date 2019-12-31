package guru.bug.austras.convert.json;

import guru.bug.austras.convert.Converts;
import guru.bug.austras.core.qualifiers.Qualifier;
import guru.bug.austras.core.qualifiers.QualifierProperty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier(name = Converts.QUALIFIER_NAME, properties = @QualifierProperty(name = Converts.PROPERTY_TYPE, value = "application/json"))
public @interface ApplicationJson {
}
