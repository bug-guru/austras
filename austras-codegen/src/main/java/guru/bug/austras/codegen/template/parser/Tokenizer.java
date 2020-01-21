/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen.template.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Tokenizer<T> {
    private final List<TokenProcessor<T>> variants;

    Tokenizer(List<TokenProcessor<T>> variants) {
        this.variants = variants;
    }

    public List<T> process(String content) {
        var resultTokens = new ArrayList<T>();
        var codePoints = IntStream.concat(content.codePoints(), IntStream.of(-1));
        var holder = new StateHolder<T>();
        resetVariants();
        codePoints.forEach(cp -> processCodepoint(cp, resultTokens, holder));
        resetVariants();
        return resultTokens;
    }

    private void processCodepoint(int cp, ArrayList<T> resultTokens, StateHolder<T> holder) {
        do {
            holder.repeat = false;
            if (cp == -1) {
                processEOF(resultTokens, holder);
            } else {
                holder.processed = false;
                List<TokenProcessor<T>> toProcess = whatToProcess(holder);
                processAll(toProcess, cp, resultTokens, holder);
                if (!holder.processed) {
                    throw new IllegalStateException("token not processed " + Character.toString(cp));
                }
            }
        } while (holder.repeat);
    }

    private void processAll(List<TokenProcessor<T>> processors, int cp, ArrayList<T> resultTokens, StateHolder<T> stateHolder) {
        for (var processor : processors) {
            var result = processor.process(cp);
            switch (result) {
                case REJECT:
                    processor.reset();
                    break;
                case ACCEPT_FORCE_NEXT:
                    stateHolder.forced = processor;
                    accept(processor, resultTokens, stateHolder);
                    return;
                case ACCEPT:
                    accept(processor, resultTokens, stateHolder);
                    return;
                case COMPLETE_REWIND:
                    stateHolder.repeat = true;
                    complete(processor, resultTokens, stateHolder);
                    return;
                case COMPLETE:
                    complete(processor, resultTokens, stateHolder);
                    return;
                default:
                    throw new IllegalArgumentException("Not supported " + result);
            }
        }
    }

    private void accept(TokenProcessor<T> processor, ArrayList<T> resultTokens, StateHolder<T> stateHolder) {
        if (stateHolder.lastAccepted != null && stateHolder.lastAccepted != processor) {
            T token = stateHolder.lastAccepted.complete();
            stateHolder.lastAccepted.reset();
            if (token != null) {
                resultTokens.add(token);
            }
        }
        stateHolder.lastAccepted = processor;
        stateHolder.processed = true;
    }

    private void complete(TokenProcessor<T> processor, ArrayList<T> resultTokens, StateHolder<T> stateHolder) {
        stateHolder.forced = null;
        stateHolder.lastAccepted = null;
        stateHolder.processed = true;
        T token = processor.complete();
        processor.reset();
        if (token != null) {
            resultTokens.add(token);
        }
    }

    private List<TokenProcessor<T>> whatToProcess(StateHolder<T> holder) {
        if (holder.forced == null) {
            return this.variants;
        } else {
            var result = List.of(holder.forced);
            holder.forced = null;
            return result;
        }
    }

    private void processEOF(ArrayList<T> resultTokens, StateHolder<T> holder) {
        if (holder.lastAccepted != null) {
            T token = holder.lastAccepted.complete();
            holder.lastAccepted.reset();
            if (token != null) {
                resultTokens.add(token);
            }
        }
    }

    private void resetVariants() {
        variants.forEach(TokenProcessor::reset);
    }

    private static class StateHolder<T> {
        TokenProcessor<T> forced;
        TokenProcessor<T> lastAccepted;
        boolean processed;
        boolean repeat;
    }


}
