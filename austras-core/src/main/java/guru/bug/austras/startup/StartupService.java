/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.startup;

import jakarta.servlet.ServletContext;

public interface StartupService {
    void initialize(ServletContext ctx);

    void destroy(ServletContext ctx);
}
