package guru.bug.austras.codetempl.parser.tokenizer;

class SpaceIgnoreTokenProcessor implements TokenProcessor<String> {

    @Override
    public ProcessResult process(int codePoint) {
        if (codePoint == ' ') {
            return ProcessResult.ACCEPT;
        } else {
            return ProcessResult.REJECT;
        }
    }

    @Override
    public String complete() {
        return null;
    }
}
