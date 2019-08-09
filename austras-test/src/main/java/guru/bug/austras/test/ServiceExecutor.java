package guru.bug.austras.test;

import java.util.Collection;

public class ServiceExecutor {
    private final Collection<Service> services;

    public ServiceExecutor(Collection<Service> services) {
        this.services = services;
    }

    public void runAll() {
        for (var s : services) {
            s.execute();
        }
    }
}
