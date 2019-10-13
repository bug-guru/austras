package guru.bug.austras.codetempl;

import guru.bug.austras.codetempl.blocks.Block;

import java.io.PrintWriter;
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
