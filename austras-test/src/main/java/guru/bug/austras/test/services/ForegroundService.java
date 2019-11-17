package guru.bug.austras.test.services;

import guru.bug.austras.startup.StartupService;
import guru.bug.austras.test.broadcast.SenderStrComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ForegroundService implements StartupService, Runnable {
    private static final Logger log = LoggerFactory.getLogger(ForegroundService.class);
    private final Thread thread = new Thread(this);
    private volatile boolean interrupt = false;
    private final SenderStrComponent sender;

    public ForegroundService(SenderStrComponent sender) {
        this.sender = sender;
    }


    @Override
    public void initialize() {
        log.info("Starting foreground service");
        thread.start();
        log.info("Foreground service is started");
    }

    @Override
    public void destroy() {
        log.info("Stopping foreground service");
        if (interrupt) {
            log.warn("Already stopped");
            return;
        }
        interrupt = true;
        try {
            thread.join();
        } catch (InterruptedException e) {
            log.warn("Stopping service unsuccessful", e);
            Thread.currentThread().interrupt();
        }
        log.info("Foreground service is stopped");
    }


    @Override
    public void run() {
        while (!interrupt) {
            try {
                Thread.sleep(5000);
                sender.sendHello();
            } catch (InterruptedException e) {
                log.warn("interrupted", e);
                interrupt = true;
                Thread.currentThread().interrupt();
            }
        }
    }
}
