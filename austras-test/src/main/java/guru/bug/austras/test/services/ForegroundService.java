package guru.bug.austras.test.services;

import guru.bug.austras.startup.StartupService;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ForegroundService implements StartupService, Runnable {
    private static final Logger log = Logger.getLogger(ForegroundService.class.getName());
    private final Thread thread = new Thread(this);
    private volatile boolean interrupt = false;

    @Override
    public void initialize() {
        log.info("Starting foreground service");
        thread.start();
        log.info("Foreground service is started");
    }

    @Override
    public void destroy() {
        log.info("Stopping foreground service");
        interrupt = true;
        try {
            thread.join();
        } catch (InterruptedException e) {
            log.log(Level.WARNING, "Stopping service unsuccessful", e);
        }
        log.info("Foreground service is stopped");
    }


    @Override
    public void run() {
        while (!interrupt) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
