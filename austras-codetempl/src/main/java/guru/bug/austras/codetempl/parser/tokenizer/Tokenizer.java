package guru.bug.austras.codetempl.parser.tokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Tokenizer<T> {
    private final List<TokenProcessor<T>> variants;

    public Tokenizer(List<TokenProcessor<T>> variants) {
        this.variants = variants;
    }

    public List<T> process(String content) {
        var resultTokens = new ArrayList<T>();
        var codePoints = IntStream.concat(content.codePoints(), IntStream.of(-1));
        var holder = new Object() {
            TokenProcessor<T> forced;
            TokenProcessor<T> lastAccepted;
        };
        codePoints.forEach(cp -> {
            if (cp == -1) {
                if (holder.lastAccepted != null) {
                    T token = holder.lastAccepted.complete();
                    if (token != null) {
                        resultTokens.add(token);
                    }
                }
            } else {
                List<TokenProcessor<T>> toProcess;
                boolean processed = false;
                if (holder.forced == null) {
                    toProcess = this.variants;
                } else {
                    toProcess = List.of(holder.forced);
                    holder.forced = null;
                }
                loop:
                for (var p : toProcess) {
                    var result = p.process(cp);
                    switch (result) {
                        case REJECT:
                            break;
                        case ACCEPT_FORCE_NEXT:
                            holder.forced = p;
                        case ACCEPT:
                            if (holder.lastAccepted != null && holder.lastAccepted != p) {
                                T token = holder.lastAccepted.complete();
                                if (token != null) {
                                    resultTokens.add(token);
                                }
                            }
                            holder.lastAccepted = p;
                            processed = true;
                            break loop;
                        case COMPLETE:
                            holder.forced = null;
                            holder.lastAccepted = null;
                            processed = true;
                            T token = p.complete();
                            if (token != null) {
                                resultTokens.add(token);
                            }
                            break loop;
                    }
                }
                if (!processed) {
                    throw new IllegalStateException("token not processed " + Character.toString(cp));
                }
            }
        });
        return resultTokens;
    }


}
