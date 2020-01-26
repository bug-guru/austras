/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen;

@Template(file = "ComponentGenerator.java.txt")
public abstract class ComponentGenerator extends JavaFileGenerator {


    @Override
    public abstract String getPackageName();

    @Override
    public abstract String getSimpleClassName();

    @Template(name = "QUALIFIED_CLASS_NAME")
    public String getQualifiedClassName() {
        return getPackageName() + "." + getSimpleClassName();
    }

}
