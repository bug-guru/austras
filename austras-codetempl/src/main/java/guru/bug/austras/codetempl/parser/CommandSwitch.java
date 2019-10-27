package guru.bug.austras.codetempl.parser;

import guru.bug.austras.codetempl.blocks.Block;

public class CommandSwitch {
    private final LoopCommandParser loopCommandParser;
    private final BodyTokensProcessor bodyTokensProcessor;

    public CommandSwitch(BodyTokensProcessor bodyTokensProcessor) {
        this.bodyTokensProcessor = bodyTokensProcessor;
        this.loopCommandParser = new LoopCommandParser(bodyTokensProcessor);
    }

    public Block parse(String value) {
        String cmd = value.split("\\s", 2)[0];
        //noinspection SwitchStatementWithTooFewBranches
        switch (cmd) {
            case "LOOP":
                return loopCommandParser.parse(value);
            default:
                throw new IllegalArgumentException("Unknown command " + cmd);
        }
    }
}
