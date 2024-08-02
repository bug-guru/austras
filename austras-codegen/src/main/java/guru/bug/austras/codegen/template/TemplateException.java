/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen.template;

public class TemplateException extends RuntimeException {
    public TemplateException(String msg) {
        super(msg);
    }

    public TemplateException(Exception cause) {
        super(cause);
    }
}
