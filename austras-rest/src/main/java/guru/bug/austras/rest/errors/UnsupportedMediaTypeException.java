/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.rest.errors;

public class UnsupportedMediaTypeException extends HttpException {
    public UnsupportedMediaTypeException() {
        super(415, "Unsupported Media Type");
    }
}
