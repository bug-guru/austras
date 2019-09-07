package guru.bug.austras.test.services;

import guru.bug.austras.startup.StartupService;

public class DaemonService implements StartupService {
    @Override
    public void initialize() {
        System.out.println("DAEMON SERVICE INITIALIZED!");
    }

    @Override
    public void destroy() {
        System.out.println("DAEMON SERVICE DESTROYED!");
    }
}
