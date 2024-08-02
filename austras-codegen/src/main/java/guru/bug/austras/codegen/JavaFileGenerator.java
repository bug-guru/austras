/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen;

import guru.bug.austras.codegen.template.TemplateException;

import javax.annotation.processing.Filer;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

@Template(file = "JavaFile.java.txt")
public abstract class JavaFileGenerator extends FileGenerator {
    private final ImportsManager importsManager = new ImportsManager();
    private String qualifiedClassName;

    @Template(name = "IMPORTS_SECTION")
    public void writeImportsSection(PrintWriter out) {
        importsManager.processImports(out);
    }

    @Template(name = "IMPORTS")
    public void collectImports(BodyProcessor body) {
        body.processAndGetBody().lines()
                .map(String::strip)
                .filter(l -> !l.isEmpty())
                .forEach(importsManager::requireImport);
    }

    @Template(name = "PACKAGE_NAME")
    public abstract String getPackageName();

    @Template(name = "QUALIFIED_CLASS_NAME")
    public String getGetQualifiedClassName() {
        return qualifiedClassName;
    }

    @Template(name = "SIMPLE_CLASS_NAME")
    public abstract String getSimpleClassName();

    protected void generate(Filer filer) {
        var pkgName = getPackageName();
        var clsName = getSimpleClassName();
        this.qualifiedClassName = pkgName + "." + clsName;

        try (var w = filer.createSourceFile(qualifiedClassName).openWriter();
             var out = new PrintWriter(w)) {
            generate(out);
        } catch (IOException e) {
            throw new TemplateException(e);
        }
    }

    @Override
    protected void generate(PrintWriter out) {
        try {
            importsManager.initPackage(getPackageName());
            // first time processing template with null output just to collect imports
            super.generate(new PrintWriter(Writer.nullWriter()));
            // second time processing - write template
            super.generate(out);
        } finally {
            importsManager.clear();
        }
    }


    protected String tryImport(String qualifiedName) {
        return importsManager.tryImport(qualifiedName);
    }
}
