package guru.bug.austras.codetempl;

import guru.bug.austras.codetempl.blocks.Block;
import guru.bug.austras.codetempl.parser.BodyTokensProcessor;
import guru.bug.austras.codetempl.parser.tokenizer.template.TemplateTokenizer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Template {

    private final List<Block> blocks;

    private Template(List<Block> blocks) {
        this.blocks = blocks;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void execute(Context global, PrintWriter out) {
        var printables = new ArrayList<Printable>();

        for (Block block : blocks) {
            var p = block.evaluate(global);
            printables.addAll(p);
        }

        for (Printable printable : printables) {
            printable.print(out);
        }

    }

    public static Template createFrom(InputStream is) throws IOException {
        try (var reader = new InputStreamReader(is)) {
            return createFrom(reader);
        }
    }

    public static Template createFrom(Reader reader) throws IOException {
        var contentWriter = new StringWriter(2048);
        reader.transferTo(contentWriter);
        String content = contentWriter.toString();
        return createFrom(content);
    }

    public static Template createFrom(String content) {
        var tokenizer = new TemplateTokenizer();
        var tokens = tokenizer.process(content);
        var tokenIterator = tokens.iterator();
        var processor = new BodyTokensProcessor(tokenIterator);
        var blocks = processor.processBody();
        if (tokenIterator.hasNext()) {
            throw new IllegalStateException("Asymmetric #END#");
        }
        return new Template(blocks);
    }

    public static class Builder {
        private final List<Block> blocks = new ArrayList<>();

        public Builder add(Block block) {
            this.blocks.add(block);
            return this;
        }

        public Builder add(List<Block> blocks) {
            this.blocks.addAll(blocks);
            return this;
        }

        public Template build() {
            return new Template(blocks);
        }
    }
}
