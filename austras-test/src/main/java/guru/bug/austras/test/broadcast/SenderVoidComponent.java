/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.test.broadcast;

import guru.bug.austras.core.qualifiers.Broadcast;

public class SenderVoidComponent {
    private final VoidEvents voidEvents;

    public SenderVoidComponent(@Broadcast VoidEvents voidEvents) {
        this.voidEvents = voidEvents;
    }

    public void sendNotification() {
        voidEvents.notification();
    }
}
