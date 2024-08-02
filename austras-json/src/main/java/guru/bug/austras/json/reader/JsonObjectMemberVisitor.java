/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.json.reader;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@FunctionalInterface
public interface JsonObjectMemberVisitor<T> {
    void accept(T object, String key, JsonValueReader reader);
}
