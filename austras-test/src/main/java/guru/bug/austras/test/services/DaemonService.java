/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.test.services;

import guru.bug.austras.startup.StartupService;
import guru.bug.austras.test.broadcast.SenderVoidComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletContext;

public class DaemonService implements StartupService {
    private static final Logger log = LoggerFactory.getLogger(DaemonService.class);
    private final SenderVoidComponent sender;

    public DaemonService(SenderVoidComponent sender) {
        this.sender = sender;
    }


    @Override
    public void initialize(ServletContext ctx) {
        log.info("DAEMON SERVICE INITIALIZED!");
        sender.sendNotification();
    }

    @Override
    public void destroy(ServletContext ctx) {
        sender.sendNotification();
        log.info("DAEMON SERVICE DESTROYED!");
    }
}
