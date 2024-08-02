/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.rest.errors;

public class HttpException extends Exception {
    private final int statusCode;
    private final String httpMessage;

    public HttpException(int statusCode, String message) {
        this(statusCode, message, message);
    }

    public HttpException(int statusCode, String httpMessage, String message) {
        super(message);
        this.httpMessage = httpMessage;
        this.statusCode = statusCode;
    }

    public String getHttpMessage() {
        return httpMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
