package guru.bug.austras.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.lang.String.format;

public class StartupServicesStarter {
    private static final Logger log = LoggerFactory.getLogger(StartupServicesStarter.class);
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
            log.info("Initializing {} services", services.size());
            services.forEach(this::init);
        }
    }

    private void init(StartupService startupService) {
        log.info("Initializing service {}", startupService.getClass().getName());
        try {
            startupService.initialize();
            initializedStartupServices.add(startupService);
        } catch (Exception e) {
            var msg = format("Error initializing service %s", startupService.getClass().getName());
            log.error(msg, e);
            System.exit(1);
            throw new IllegalStateException(msg, e);
        }
    }

    private class Terminator extends Thread {
        public Terminator() {
            super(StartupServicesStarter.class.getSimpleName() + "-shutdown-hook");
        }

        @Override
        public void run() {
            log.info("Stopping {} services", initializedStartupServices.size());
            initializedStartupServices.forEach(this::destroy);
            log.info("Application is stopped");
        }

        private void destroy(StartupService startupService) {
            try {
                log.info("Stopping service {}", startupService.getClass().getName());
                startupService.destroy();
            } catch (Exception e) {
                log.error("Error stopping service " + startupService.getClass().getName(), e);
            }
        }
    }
}
