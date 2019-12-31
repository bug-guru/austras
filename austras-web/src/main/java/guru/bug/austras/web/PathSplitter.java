/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class PathSplitter {
    private PathSplitter() {
    }

    public static <T> List<T> split(Function<String, T> mapper, String path) {
        return List.copyOf(
                split(path).stream()
                        .map(mapper)
                        .collect(Collectors.toList())
        );
    }

    public static <T> List<T> split(Function<String, T> mapper, List<String> fragments) {
        return List.copyOf(
                fragments.stream()
                        .map(PathSplitter::split)
                        .flatMap(Collection::stream)
                        .map(mapper)
                        .collect(Collectors.toList())
        );
    }

    private static List<String> split(String path) {
        var result = new ArrayList<String>();
        int idx;
        int lastIdx = 0;
        while (lastIdx < path.length() && (idx = path.indexOf('/', lastIdx)) >= 0) {
            if (lastIdx < idx) {
                var p = path.substring(lastIdx, idx);
                result.add(p);
            }
            lastIdx = idx + 1;
        }
        if (lastIdx < path.length()) {
            var p = path.substring(lastIdx);
            result.add(p);
        }
        return result;
    }

}