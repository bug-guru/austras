package guru.bug.fuzzbagel.apt.core;

import javax.annotation.processing.AbstractProcessor;
import javax.tools.Diagnostic;

import static javax.tools.Diagnostic.Kind.*;

public abstract class AbstractFuzzBagelAnnotationProcessor extends AbstractProcessor
    implements Logger {

  @Override
  public void log(Diagnostic.Kind kind, String msg, Object... args) {
    if (args == null || args.length == 0) {
      processingEnv.getMessager().printMessage(kind, msg);
    } else {
      processingEnv.getMessager().printMessage(kind, String.format(msg, args));
    }
  }

  @Override
  public void debug(String msg, Object... args) {
    log(NOTE, msg, args);
  }

  @Override
  public void warn(String msg, Object... args) {
    warn(false, msg, args);
  }

  @Override
  public void warn(boolean mandatory, String msg, Object... args) {
    log(mandatory ? MANDATORY_WARNING : WARNING, msg, args);
  }

  @Override
  public void error(String msg, Object... args) {
    log(ERROR, msg, args);
  }
}
