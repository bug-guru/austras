/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.web.errors;

public class NotFoundException extends HttpException {
    public NotFoundException() {
        super(404, "Not Found");
    }
}
