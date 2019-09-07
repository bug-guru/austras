package guru.bug.austras.startup;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.String.format;

public class StartupServicesStarter {
    private static final Logger log = Logger.getLogger(StartupServicesStarter.class.getName());
    private final Collection<? extends StartupService> services;
    private final List<StartupService> initializedStartupServices = new ArrayList<>();

    public StartupServicesStarter(Collection<? extends StartupService> services) {
        this.services = services;
    }

    public void initAll() {
        Runtime.getRuntime().addShutdownHook(new Terminator());
        if (services == null || services.isEmpty()) {
            log.info("No services found. Nothing to initialize.");
        } else {
            log.info(() -> format("Initializing %d services", services.size()));
            services.forEach(this::init);
        }
    }

    private void init(StartupService startupService) {
        log.info(() -> format("Initializing service %s", startupService.getClass().getName()));
        try {
            startupService.initialize();
            initializedStartupServices.add(startupService);
        } catch (Exception e) {
            var msg = format("Error initializing service %s", startupService.getClass().getName());
            log.log(Level.SEVERE, msg, e);
            System.exit(1);
            throw new IllegalStateException(msg, e);
        }
    }

    private class Terminator extends Thread {

        // System out is being used because logging has its own shutdown hook and may be stopped before this.
        @Override
        public void run() {
            System.out.printf("Stopping %d services\n", initializedStartupServices.size());
            initializedStartupServices.forEach(this::destroy);
            System.out.println("Application is stopped");
        }

        private void destroy(StartupService startupService) {
            try {
                System.out.printf("Stopping service %s\n", startupService.getClass().getName());
                startupService.destroy();
            } catch (Exception e) {
                var buf = new StringWriter();
                var out = new PrintWriter(buf);
                out.printf("Error stopping service %s: ", startupService.getClass().getName());
                e.printStackTrace(out);
                out.flush();
                out.close();
                System.out.println(buf.toString());
            }
        }
    }
}
