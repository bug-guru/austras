/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen;

public interface JavaCodeWriter {

    void writeQualifiedName(String qualifiedName);

    void writeStringLiteral(String str);

    void writeRaw(String str);

}
