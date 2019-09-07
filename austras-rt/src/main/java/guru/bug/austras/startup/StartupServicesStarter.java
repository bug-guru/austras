package guru.bug.austras.startup;

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
            return;
        }
        services.forEach(this::init);
    }

    private void init(StartupService startupService) {
        log.info(() -> format("Initializing service %s", startupService.getClass()));
        try {
            startupService.initialize();
            initializedStartupServices.add(startupService);
        } catch (Exception e) {
            var msg = format("Error initializing service %s", startupService.getClass());
            log.log(Level.SEVERE, msg, e);
            System.exit(1);
            throw new IllegalStateException(msg, e);
        }
    }

    private class Terminator extends Thread {
        private final Logger log = Logger.getLogger(Terminator.class.getName());

        @Override
        public void run() {
            initializedStartupServices.forEach(this::destroy);
        }

        private void destroy(StartupService startupService) {
            try {
                startupService.destroy();
            } catch (Exception e) {
                log.log(Level.WARNING, "Error destroying service " + startupService.getClass(), e);
            }
        }
    }
}
