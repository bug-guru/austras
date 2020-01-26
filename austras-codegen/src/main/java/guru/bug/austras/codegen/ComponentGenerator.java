/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen;

@Template(file = "ComponentGenerator.java.txt")
public class ComponentGenerator extends JavaFileGenerator {


    @Override
    public String getPackageName() {
        return null;
    }

    @Override
    public String getSimpleClassName() {
        return null;
    }
}
