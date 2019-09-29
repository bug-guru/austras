package guru.bug.austras.apt.core.logging;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

public class AustrasLogging {
    private static final DateTimeFormatter FILENAME_DT_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    public static void setup() {
        Logger logger = Logger.getLogger("guru.bug.austras");
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);
        var f = new AustrasAptLoggerFormatter();
        FileOutputStream out;
        try {
            Files.createDirectories(Paths.get("target"));
            out = new FileOutputStream("target/austras-" + LocalDateTime.now().format(FILENAME_DT_FORMATTER) + ".log");
        } catch (IOException e) {
            throw new IllegalStateException(e); // TODO better error handling
        }
        var h = new StreamHandler(out, f);
        h.setLevel(Level.ALL);
        logger.addHandler(h);
    }
}
