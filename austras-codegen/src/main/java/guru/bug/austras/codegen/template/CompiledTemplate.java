/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen.template;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CompiledTemplate {
    private final BlockWithBody body;

    public CompiledTemplate(String content) {
        TemplateTokenizer tokenizer = new TemplateTokenizer();
        var tokens = tokenizer.process(content);
        TemplateCleaner.cleanup(tokens);

        var tokenIterator = tokens.iterator();
        body = new BlockWithBody(null, tokenIterator);
    }

    public void writeTo(PrintWriter out, TemplateCaller resolver) {
        body.writeTo(out, resolver);
    }

    private static class TextBlock implements Block {
        private final String text;

        private TextBlock(String text) {
            this.text = text;
        }

        @Override
        public void writeTo(PrintWriter out, TemplateCaller caller) {
            out.print(text);
        }
    }

    private static class NewLineBlock implements Block {

        @Override
        public void writeTo(PrintWriter out, TemplateCaller caller) {
            out.println();
        }
    }

    private class ValueBlock implements Block {

        private final String name;

        public ValueBlock(String name) {
            this.name = name;
        }

        @Override
        public void writeTo(PrintWriter out, TemplateCaller caller) {
            caller.call(name, this, out);
        }
    }

    private class BlockWithBody implements Block {
        private final String name;
        private final List<Block> blocks;

        private BlockWithBody(String name, Iterator<TemplateToken> tokenIterator) {
            this.name = name;
            this.blocks = collectBlocks(tokenIterator);
        }

        private List<Block> collectBlocks(Iterator<TemplateToken> tokenIterator) {
            var result = new ArrayList<Block>();
            while (tokenIterator.hasNext()) {
                Block block;
                var t = tokenIterator.next();
                switch (t.getType()) {
                    case NEW_LINE:
                        block = new NewLineBlock();
                        break;
                    case TEXT:
                        block = new TextBlock(t.getValue());
                        break;
                    case VALUE:
                        block = new ValueBlock(t.getValue());
                        break;
                    case BLOCK:
                        if ("END".equals(t.getValue())) {
                            result.trimToSize();
                            return result;
                        }
                        block = new BlockWithBody(t.getValue(), tokenIterator);
                        break;
                    default:
                        throw new IllegalStateException("Unsupported token " + t.getType());
                }
                result.add(block);
            }
            result.trimToSize();
            return result;
        }

        @Override
        public void writeTo(PrintWriter out, TemplateCaller caller) {
            caller.call(name, this, out);
        }

    }

}
