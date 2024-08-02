/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletContext;
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

    public void initAll(ServletContext ctx) {
        if (services == null || services.isEmpty()) {
            log.info("No services found. Nothing to initialize.");
        } else {
            log.info("Initializing {} services", services.size());
            services.forEach(s -> init(s, ctx));
        }
    }

    private void init(StartupService startupService, ServletContext ctx) {
        log.info("Initializing service {}", startupService.getClass().getName());
        try {
            startupService.initialize(ctx);
            initializedStartupServices.add(startupService);
        } catch (Exception e) {
            log.error(format("Error initializing service %s", startupService.getClass().getName()), e);
            System.exit(1);
        }
    }

    public void destroyAll(ServletContext ctx) {
        log.info("Stopping {} services", initializedStartupServices.size());
        initializedStartupServices.forEach(s -> destroy(s, ctx));
        log.info("Application is stopped");

    }

    private void destroy(StartupService startupService, ServletContext ctx) {
        try {
            log.info("Stopping service {}", startupService.getClass().getName());
            startupService.destroy(ctx);
        } catch (Exception e) {
            log.error("Error stopping service " + startupService.getClass().getName(), e);
        }
    }
}
