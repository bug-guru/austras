package guru.bug.austras.apt.core.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class AustrasAptLoggerFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        StringWriter sw = new StringWriter();
        var out = new PrintWriter(sw);

        String loggerName = record.getLoggerName();
        if (loggerName.length() > 25) {
            loggerName = loggerName.substring(loggerName.length() - 25);
        }
        out.printf("[%-6s][%-25s] %s", record.getLevel(), loggerName, record.getMessage());
        if (record.getThrown() != null) {
            out.print(" ");
            record.getThrown().printStackTrace(out);
        }
        out.println();
        return sw.toString();
    }
}
