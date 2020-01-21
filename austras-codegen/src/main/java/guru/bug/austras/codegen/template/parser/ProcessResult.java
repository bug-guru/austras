/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen.template.parser;

enum ProcessResult {
    REJECT,
    ACCEPT,
    ACCEPT_FORCE_NEXT,
    COMPLETE,
    COMPLETE_REWIND
}
