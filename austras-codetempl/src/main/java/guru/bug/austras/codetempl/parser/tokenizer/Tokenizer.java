package guru.bug.austras.codetempl.parser.tokenizer;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class Tokenizer<T> {
    private final String content;
    private final IntStream codePoints;


    public Tokenizer(String content) {
        this.content = content;
        this.codePoints = IntStream.concat(content.codePoints(), IntStream.of(-1));
    }

    public void process(Consumer<T> tokenPush, TokenProcessor<T>... variants) {
        var variantsList = List.of(variants);
        var holder = new Object() {
            TokenProcessor<T> forced;
            TokenProcessor<T> lastAccepted;
        };
        codePoints.forEach(cp -> {
            if (cp == -1) {
                if (holder.lastAccepted != null) {
                    T token = holder.lastAccepted.complete();
                    if (token != null) {
                        tokenPush.accept(token);
                    }
                }
            } else {
                List<TokenProcessor<T>> toProcess;
                boolean processed = false;
                if (holder.forced == null) {
                    toProcess = variantsList;
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
                                    tokenPush.accept(token);
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
                                tokenPush.accept(token);
                            }
                            break loop;
                    }
                }
                if (!processed) {
                    throw new IllegalStateException("token not processed " + Character.toString(cp));
                }
            }
        });
    }


}
