package guru.bug.fuzzbagel.apt.core;

import javax.tools.Diagnostic;

public interface Logger {
  void log(Diagnostic.Kind kind, String msg, Object... args);

  void debug(String msg, Object... args);

  void warn(String msg, Object... args);

  void warn(boolean mandatory, String msg, Object... args);

  void error(String msg, Object... args);
}
