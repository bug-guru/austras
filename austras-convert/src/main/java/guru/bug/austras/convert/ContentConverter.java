/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public interface ContentConverter<T> {
    T fromString(String value);

    String toString(T value);

    T read(Reader reader) throws IOException;

    void write(T value, Writer writer) throws IOException;
}
