package guru.bug.austras.annotations;

import guru.bug.austras.provider.ScopeCache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.PARAMETER})
public @interface NoCached {
}
