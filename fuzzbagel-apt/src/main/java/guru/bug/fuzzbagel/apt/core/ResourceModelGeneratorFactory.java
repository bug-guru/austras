package guru.bug.fuzzbagel.apt.core;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Set;

public interface ResourceModelGeneratorFactory {

  default Set<Class<? extends Annotation>> supportedAnnotations() {
    return Collections.emptySet();
  }

  default int getOrder() {
    return 1000;
  }
}
