/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
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
    private final Filer filer;

    protected JavaFileGenerator(Filer filer) {
        this.filer = filer;
    }

    @Template(name = "IMPORTS")
    public void imports(PrintWriter out) {
        importsManager.processImports();
    }

    @Template(name = "PACKAGE_NAME")
    public abstract String getPackageName();

    public abstract String getSimpleClassName();

    protected final void generate() {
        var pkgName = getPackageName();
        var clsName = getSimpleClassName();
        var qName = pkgName + "." + clsName;

        importsManager.initPackage(pkgName);

        try (var w = filer.createSourceFile(qName).openWriter();
             var out = new PrintWriter(w)) {
            // first time processing template with null output just to collect imports
            super.generate(new PrintWriter(Writer.nullWriter()));
            // second time processing - write template
            super.generate(out);
        } catch (IOException e) {
            throw new TemplateException(e);
        } finally {
            importsManager.clear();
        }
    }

    protected String tryImport(String qualifiedName) {
        return importsManager.tryImport(qualifiedName);
    }
}
