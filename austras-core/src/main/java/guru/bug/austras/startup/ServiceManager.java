/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.lang.String.format;

public class ServiceManager {
    private static final Logger log = LoggerFactory.getLogger(ServiceManager.class);
    private final Collection<? extends StartupService> services;
    private final List<StartupService> initializedStartupServices = new ArrayList<>();

    public ServiceManager(Collection<? extends StartupService> services) {
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
            log.error(format("Error initializing service %s", startupService.getClass().getName()), e);
            System.exit(1);
        }
    }

    private class Terminator extends Thread {
        private Terminator() {
            super(ServiceManager.class.getSimpleName() + "-shutdown");
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
