package guru.bug.austras.codetempl.blocks;

import guru.bug.austras.codetempl.Context;
import guru.bug.austras.codetempl.Printable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ParamsBlock implements Block {
    private final String loopVar;
    private final String collectionVar;
    private final List<Block> body;

    private ParamsBlock(String loopVar, String collectionVar, List<Block> body) {
        this.loopVar = loopVar;
        this.collectionVar = collectionVar;
        this.body = List.copyOf(body);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public List<Printable> evaluate(Context ctx) {
        var result = new ArrayList<Printable>();
        var collection = ctx.get(collectionVar);
        var size = collection.getValueAt("SIZE").getInteger();
        for (int i = 0; i < size; i++) {
            var value = collection.getValueAt(i);
            var subCtx = ctx.childBuilder()
                    .put(loopVar, value)
                    .build();
            result.addAll(evaluateBody(subCtx));
            if (i < size - 1) {
                result.add((p) -> p.print(", "));
            }
        }
        return result;
    }

    private List<Printable> evaluateBody(Context ctx) {
        var result = new ArrayList<Printable>();
        for (Block block : body) {
            result.addAll(block.evaluate(ctx));
        }
        return result;
    }

    public static class Builder {
        private String loopVar;
        private String collectionVar;
        private List<Block> body = new ArrayList<>();

        private Builder() {

        }

        public Builder loopVar(String loopVar) {
            this.loopVar = loopVar;
            return this;
        }

        public Builder collectionVar(String collectionVar) {
            this.collectionVar = collectionVar;
            return this;
        }

        public Builder addBlock(Block block) {
            this.body.add(block);
            return this;
        }

        public Builder addBlocks(Block... blocks) {
            this.body.addAll(List.of(blocks));
            return this;
        }

        public Builder addBlocks(Collection<Block> blocks) {
            this.body.addAll(blocks);
            return this;
        }

        public ParamsBlock build() {
            return new ParamsBlock(loopVar, collectionVar, body);
        }

    }
}
