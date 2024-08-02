/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public interface ByteContentConverter {
    byte fromString(String value);

    String toString(byte value);

    byte read(Reader reader) throws IOException;

    void write(byte value, Writer writer) throws IOException;
}
