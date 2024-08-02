/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.json.utils;

class JsonException extends RuntimeException {

    JsonException(String message) {
        super(message);
    }

    JsonException(String message, Throwable cause) {
        super(message, cause);
    }

    JsonException(Throwable cause) {
        super(cause);
    }
}
