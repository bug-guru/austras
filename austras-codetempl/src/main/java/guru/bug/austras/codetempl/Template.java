package guru.bug.austras.codetempl;

import guru.bug.austras.codetempl.blocks.Block;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Template {

    private final List<Block> blocks;

    public Template(List<Block> blocks) {
        this.blocks = blocks;
    }

    public void execute(Context global, PrintWriter out) {
        var printables = new ArrayList<Printable>();

        for (Block block : blocks) {
            Context ctx = new Context(global);
            var p = block.evaluate(ctx);
            printables.addAll(p);
        }

        for (Printable printable : printables) {
            printable.print(out);
        }

    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        public Template build() {

        }
    }
}
