/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen;

import java.util.List;
import java.util.ListIterator;

final class TemplateCleaner {
    private TemplateCleaner() {

    }

    public static void cleanup(List<TemplateToken> tokens) {
        cleanupSpaces(tokens);
        cleanupNewLines(tokens);
    }

    private static void cleanupSpaces(List<TemplateToken> tokens) {
        var i = tokens.listIterator();
        var state = new CleanupState(1);
        boolean hasTrailingSpaces = false;
        boolean hasLeadingSpaces = false;

        while (i.hasNext()) {
            state.set(i.next());
            if (state.in(0, 1) && state.isNewLine()) {
                state.set(1);
            } else if (state.in(1) && state.isBlankText()) {
                state.set(2);
                hasLeadingSpaces = true;
            } else if (state.in(1, 2) && state.isBlock()) {
                state.set(3);
            } else if (state.in(3) && state.isBlankText()) {
                state.set(4);
                hasTrailingSpaces = true;
            } else if (state.in(3, 4) && state.isNewLine()) {
                trimSpaces(i, hasTrailingSpaces, hasLeadingSpaces);
                state.set(1);
                hasLeadingSpaces = false;
                hasTrailingSpaces = false;
            } else {
                state.set(0);
                hasLeadingSpaces = false;
                hasTrailingSpaces = false;
            }
        }
    }

    private static void trimSpaces(ListIterator<TemplateToken> i, boolean hasTrailingSpaces, boolean hasLeadingSpaces) {
        i.remove();
        if (hasTrailingSpaces) {
            i.previous();
            i.remove();
        }
        if (hasLeadingSpaces) {
            i.previous();
            i.previous();
            i.remove();
            i.next();
        }
    }

    private static void cleanupNewLines(List<TemplateToken> tokens) {
        var i = tokens.listIterator();
        var state = new CleanupState(0);
        while (i.hasNext()) {
            state.set(i.next());
            if (state.in(0) && state.isBlock()) {
                state.set(1);
            } else if (state.in(1) && state.isNewLine()) {
                state.set(2);
            } else if (state.in(2) && state.isBlock()) {
                i.previous();
                i.remove();
                i.next();
                state.set(0);
            } else {
                state.set(0);
            }
        }
    }

    private static class CleanupState {
        int state;
        TemplateToken token;

        CleanupState(int initialState) {
            this.state = initialState;
        }

        boolean in(int e1) {
            return state == e1;
        }

        boolean in(int e1, int e2) {
            return state == e1 || state == e2;
        }

        void set(int state) {
            this.state = state;
        }

        void set(TemplateToken token) {
            this.token = token;
        }

        boolean isNewLine() {
            return token.getType() == TemplateToken.Type.NEW_LINE;
        }

        boolean isBlock() {
            return token.getType() == TemplateToken.Type.BLOCK;
        }

        boolean isBlankText() {
            return token.getType() == TemplateToken.Type.TEXT && token.getValue().isBlank();
        }
    }
}
