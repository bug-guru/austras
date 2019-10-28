package guru.bug.austras.codetempl.parser.template;

import guru.bug.austras.codetempl.parser.ProcessResult;
import guru.bug.austras.codetempl.parser.TokenProcessor;

public class TemplateSpecTokenProcessor implements TokenProcessor<TemplateToken> {
    private final StringBuilder content = new StringBuilder();
    private TemplateToken.Type type;
    private State state = State.READY;
    private boolean escape = false;
    private boolean string = false;

    @Override
    public ProcessResult process(int codePoint) {
        if (state == State.READY) {
            return processStart(codePoint);
        }
        if (state == State.STARTED) {
            return processBody(codePoint);
        }
        return null;
    }

    private ProcessResult processBody(int codePoint) {
        if (!string
                && (type == TemplateToken.Type.COMMAND && codePoint == '#'
                || type == TemplateToken.Type.EXPRESSION && codePoint == '$')) {
            state = State.COMPLETED;
            return ProcessResult.COMPLETE;
        }
        content.appendCodePoint(codePoint);
        if (string) {
            if (escape) {
                escape = false;
            } else if (codePoint == '\\') {
                escape = true;
            } else if (codePoint == '"') {
                string = false;
            }
        } else if (codePoint == '"') {
            string = true;
        }
        return ProcessResult.ACCEPT_FORCE_NEXT;
    }


    private ProcessResult processStart(int codePoint) {
        if (codePoint == '#') {
            type = TemplateToken.Type.COMMAND;
        } else if (codePoint == '$') {
            type = TemplateToken.Type.EXPRESSION;
        } else {
            return ProcessResult.REJECT;
        }
        state = State.STARTED;
        return ProcessResult.ACCEPT_FORCE_NEXT;
    }

    @Override
    public void reset() {
        content.setLength(0);
        type = null;
        state = State.READY;
        string = false;
        escape = false;
    }

    @Override
    public TemplateToken complete() {
        if (state != State.COMPLETED) {
            throw new IllegalStateException("Unexpected state " + state);
        }
        String body = content.toString();
        return new TemplateToken(type, body);
    }

    private enum State {READY, STARTED, COMPLETED}
}
