/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.rest.errors;

public class NotFoundException extends HttpException {
    public NotFoundException() {
        super(404, "Not Found");
    }

    public NotFoundException(String message) {
        super(404, "Not Found", message);
    }
}
