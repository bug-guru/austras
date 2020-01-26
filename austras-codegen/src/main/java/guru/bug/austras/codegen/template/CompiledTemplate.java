/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen.template;

import guru.bug.austras.codegen.template.parser.TemplateCleaner;
import guru.bug.austras.codegen.template.parser.TemplateTokenizer;

import java.io.PrintWriter;
import java.util.HashSet;

public class CompiledTemplate {
    private static final CompiledTemplate EXTENSION_TEMPLATE = new CompiledTemplate(ExtensionValueBlock.INSTANCE);
    private final Block body;

    private CompiledTemplate(Block body) {
        this.body = body;
    }

    public static CompiledTemplate compile(String content) {
        TemplateTokenizer tokenizer = new TemplateTokenizer();
        var tokens = tokenizer.process(content);
        TemplateCleaner.cleanup(tokens);

        var tokenIterator = tokens.iterator();
        var exported = new HashSet<String>();
        var body = new BlockWithBody(null, tokenIterator);
        return new CompiledTemplate(body);
    }

    public static CompiledTemplate extension() {
        return EXTENSION_TEMPLATE;
    }

    public void process(PrintWriter out, ClassTemplateCaller resolver) {
        body.process(out, resolver);
    }

    public Block getRootBlock() {
        return body;
    }
}
