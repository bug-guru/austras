package guru.bug.austras.events;

import guru.bug.austras.core.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Qualifier(name = Receiver.MESSAGE_QUALIFIER_NAME)
public @interface Message {
}
