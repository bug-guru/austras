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
import java.io.StringWriter;
import java.io.Writer;
import java.util.function.Consumer;

@Template(file = "JavaFile.java.txt")
public abstract class JavaFileGenerator extends FileGenerator {
    private final ImportsManager importsManager = new ImportsManager();

    @Template(name = "IMPORTS_SECTION")
    public void writeImportsSection(PrintWriter out) {
        importsManager.processImports(out);
    }

    @Template(name = "IMPORTS")
    public void collectImports(Consumer<PrintWriter> body) {
        var writer = new StringWriter(200);
        var out = new PrintWriter(writer);
        body.accept(out);
        var bodyText = writer.toString();
        bodyText.lines()
                .map(String::strip)
                .filter(l -> !l.isEmpty())
                .forEach(importsManager::requireImport);
    }

    @Template(name = "PACKAGE_NAME")
    public abstract String getPackageName();

    public abstract String getSimpleClassName();

    protected void generate(Filer filer) {
        var pkgName = getPackageName();
        var clsName = getSimpleClassName();
        var qName = pkgName + "." + clsName;

        try (var w = filer.createSourceFile(qName).openWriter();
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
