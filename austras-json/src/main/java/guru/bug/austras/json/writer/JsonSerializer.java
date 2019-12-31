/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.json.writer;

public interface JsonSerializer<T> {
    void toJson(T value, JsonValueWriter writer);
}
