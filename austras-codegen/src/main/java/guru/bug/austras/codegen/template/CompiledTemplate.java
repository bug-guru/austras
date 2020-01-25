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
import java.util.Set;

public class CompiledTemplate {
    private static final CompiledTemplate EXTENSION_TEMPLATE = new CompiledTemplate(ExtensionValueBlock.INSTANCE);
    private static final Block EMPTY_BLOCK = (out, caller) -> {
        // nothing
    };
    private final Block body;
    private final Set<String> exported;

    private CompiledTemplate(Block body, Set<String> exported) {
        this.body = body;
        this.exported = Set.copyOf(exported);
    }

    private CompiledTemplate(Block body) {
        this.body = body;
        this.exported = Set.of();
    }

    public static CompiledTemplate compile(String content) {
        TemplateTokenizer tokenizer = new TemplateTokenizer();
        var tokens = tokenizer.process(content);
        TemplateCleaner.cleanup(tokens);

        var tokenIterator = tokens.iterator();
        var exported = new HashSet<String>();
        var body = new BlockWithBody(null, tokenIterator, exported::add);
        return new CompiledTemplate(body, exported);
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
