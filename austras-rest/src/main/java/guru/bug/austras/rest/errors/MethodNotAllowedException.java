/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.rest.errors;

public class MethodNotAllowedException extends HttpException {
    // TODO The server MUST generate an Allow header field in a 405 response containing a list of the target resource's currently supported methods.
    public MethodNotAllowedException() {
        super(405, "Method Not Allowed");
    }

    public MethodNotAllowedException(String message) {
        super(405, "Method Not Allowed", message);
    }
}
