/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.test.broadcast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("ALL")
public class ReceiverVoid1Component implements VoidEvents {
    private static final Logger log = LoggerFactory.getLogger(ReceiverVoid1Component.class);

    @Override
    public void notification() {
        log.info("ReceiverVoid1: received notification");
    }
}
