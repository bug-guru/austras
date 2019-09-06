package guru.bug.austras.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.String.format;

public class ServicesStarter {
    private static final Logger log = Logger.getLogger(ServicesStarter.class.getName());
    private final Collection<? extends Service> services;
    private final List<Service> initializedServices = new ArrayList<>();

    public ServicesStarter(Collection<? extends Service> services) {
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

    private void init(Service service) {
        log.info(() -> format("Initializing service %s", service.getClass()));
        try {
            service.initialize();
            initializedServices.add(service);
        } catch (Exception e) {
            var msg = format("Error initializing service %s", service.getClass());
            log.log(Level.SEVERE, msg, e);
            System.exit(1);
            throw new IllegalStateException(msg, e);
        }
    }

    private class Terminator extends Thread {
        private final Logger log = Logger.getLogger(Terminator.class.getName());

        @Override
        public void run() {
            initializedServices.forEach(this::destroy);
        }

        private void destroy(Service service) {
            try {
                service.destroy();
            } catch (Exception e) {
                log.log(Level.WARNING, "Error destroying service " + service.getClass(), e);
            }
        }
    }
}
