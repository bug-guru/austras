/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen.template;

import guru.bug.austras.codegen.ProcessResult;

public interface TokenProcessor<T> {
    ProcessResult process(int codePoint);

    T complete();

    void reset();
}
