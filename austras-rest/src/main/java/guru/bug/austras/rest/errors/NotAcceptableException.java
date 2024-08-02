/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.rest.errors;

public class NotAcceptableException extends HttpException {
    // TODO If a server returns such an error status, the body of the message should contain the list of the available
    //  representations of the resources, allowing the user to choose among them.
    public NotAcceptableException() {
        super(406, "Not Acceptable");
    }
}
