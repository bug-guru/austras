/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.json.writer;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public interface JsonObjectSerializer<T> {
    void toJson(T value, JsonObjectWriter writer);
}
