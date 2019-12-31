/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.json.utils;

public class JsonWritingException extends JsonException {

    public JsonWritingException(String message) {
        super(message);
    }

    public JsonWritingException(Throwable cause) {
        super(cause);
    }
}
