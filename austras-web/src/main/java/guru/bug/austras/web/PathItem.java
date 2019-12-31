/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.web;

import java.util.Objects;

public interface PathItem {

    static PathItem matching(String value) {
        return new MatchingPathItem(value);
    }

    static PathItem param(String name) {
        return new ParamPathItem(name);
    }

    boolean canAccept(String value);

    String key();
}

class ParamPathItem implements PathItem {
    private final String key;

    ParamPathItem(String key) {
        this.key = key;
    }

    @Override
    public boolean canAccept(String value) {
        return true;
    }

    @Override
    public String key() {
        return key;
    }

    @Override
    public String toString() {
        return "{" + key + "}";
    }
}

class MatchingPathItem implements PathItem {
    private final String name;

    MatchingPathItem(String name) {
        this.name = name;
    }

    @Override
    public boolean canAccept(String value) {
        return Objects.equals(value, this.name);
    }

    @Override
    public String key() {
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}

