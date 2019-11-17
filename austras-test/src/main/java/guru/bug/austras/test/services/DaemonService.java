package guru.bug.austras.test.services;

import guru.bug.austras.startup.StartupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaemonService implements StartupService {
    private static final Logger log = LoggerFactory.getLogger(DaemonService.class);
    @Override
    public void initialize() {
        log.info("DAEMON SERVICE INITIALIZED!");
    }

    @Override
    public void destroy() {
        log.info("DAEMON SERVICE DESTROYED!");
    }
}
