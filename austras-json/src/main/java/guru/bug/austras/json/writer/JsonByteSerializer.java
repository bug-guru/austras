/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.json.writer;

public interface JsonByteSerializer {
    void toJson(byte value, JsonValueWriter writer);
}
