package guru.bug.austras.web;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathSplitter<T> {
    private final List<T> items;

    public PathSplitter(Function<String, T> mapper, List<String> fragments) {
        items = List.copyOf(
                fragments.stream()
                        .flatMap(this::split)
                        .map(mapper)
                        .collect(Collectors.toList())
        );
    }

    private Stream<String> split(String path) {
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
        return result.stream();
    }

    public List<T> getItems() {
        return items;
    }
}
