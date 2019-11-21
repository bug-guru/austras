package guru.bug.austras.test;

import java.util.Collection;

@SuppressWarnings("ALL")
public class ServiceExecutor {
    private final Collection<? extends Service> services;

    public ServiceExecutor(Collection<? extends Service> services) {
        this.services = services;
    }

    public void runAll() {
        for (var s : services) {
            s.execute();
        }
    }
}
