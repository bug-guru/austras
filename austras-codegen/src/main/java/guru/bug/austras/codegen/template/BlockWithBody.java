/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen.template;

import guru.bug.austras.codegen.template.parser.TemplateToken;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class BlockWithBody implements Block {
    private final String name;
    private final List<Block> blocks;

    BlockWithBody(String name, Iterator<TemplateToken> tokenIterator) {
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
                    block = NewLineBlock.INSTANCE;
                    break;
                case TEXT:
                    block = new TextBlock(t.getValue());
                    break;
                case VALUE:
                    var nameValue = t.getValue();
                    if (nameValue.isBlank()) {
                        block = ExtensionValueBlock.INSTANCE;
                    } else {
                        block = new ValueBlock(nameValue);
                    }
                    break;
                case BLOCK:
                    if ("END".equals(t.getValue())) {
                        result.trimToSize();
                        return result;
                    }
                    var blockName = t.getValue();
                    block = new BlockWithBody(blockName, tokenIterator);
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
    public void process(PrintWriter out, ClassTemplateCaller caller) {
        if (name == null) {
            for (var block : blocks) {
                block.process(out, caller);
            }
        } else {
            caller.callMethod(name, out, w -> {
                for (var block : blocks) {
                    block.process(w, caller);
                }
            });
        }
    }

}
