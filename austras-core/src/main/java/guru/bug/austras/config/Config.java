package guru.bug.austras.config;

import guru.bug.austras.core.Qualifier;
import guru.bug.austras.core.QualifierProperty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Qualifier(name = "ConfigurationProperty", properties = @QualifierProperty(name = "name"))
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.PARAMETER})
public @interface Config {
    String name();

    String defaultValue() default "";
}
