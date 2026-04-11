package com.baeldung.lnj;

import com.baeldung.lnj.domain.model.Task;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NewJavaFeaturesUnitTest {

    @Test
    void whenOfNullableHasValue_thenCreatesStream() {
        Task task = new Task("task-1", "Task 1", "Desc 1", null);
        Stream<Task> stream = Stream.ofNullable(task);

        assertEquals(1, stream.count());
    }

    @Test
    void whenOfNullableIsNull_thenCreatesEmptyStream() {
        // We don't have to use Stream<Task> stream = (task == null) ? Stream.empty() : Stream.of(task);
        Stream<Task> stream = Stream.ofNullable(null);

        assertEquals(0, stream.count());
    }

    @Test
    void whenUsingTakeWhile_thenStopsStreamingAfterFirstMatchingElement() {
        Stream<Integer> stream = Stream.iterate(0, i -> i + 1)
                .takeWhile(nr -> nr < 10);

        assertEquals(10, stream.count());
    }

    @Test
    void whenUsingDropWhile_thenStartsStreamingAfterFirstNotMatchingElement() {
        Stream<Integer> stream = Stream.iterate(0, i -> i + 1)
                .dropWhile(nr -> nr < 10);

        assertEquals(10, stream.findFirst().get());
    }

    @Test
    void whenUsingIterateAndPassAPredicate_thenStopsStreamingWhenPredicateIsTrue() {
        Stream<Integer> stream = Stream.iterate(0, i -> i < 10, i -> i + 1);

        assertEquals(10, stream.count());
    }

    @Test
    void whenFilteringCollectorIsUsed_thenGroupsAndFilters() {
        List<Integer> numbers = List.of(2, 4, 6, 8, 9, 10, 12, 13);

        Map<String, List<Integer>> result = numbers.stream()
                .collect(Collectors.groupingBy(
                        n -> (n % 2 == 0) ? "EVEN" : "ODD",
                        Collectors.filtering(n -> n > 8, Collectors.toList())
                ));

        assertEquals(List.of(10, 12), result.get("EVEN"));
        assertEquals(List.of(9,13), result.get("ODD"));
    }

    @Test
    void whenFlatMappingCollectorIsUsed_thenGroupsAndFlattens() {
        List<List<Integer>> listOfLists = List.of(
                List.of(1, 2),
                List.of(3, 4, 5),
                List.of(6),
                List.of(7, 8)
        );

        Map<Integer, Set<Integer>> result = listOfLists.stream()
                .collect(Collectors.groupingBy(
                        List::size,
                        Collectors.flatMapping(List::stream, Collectors.toSet())
                ));

        assertEquals(Set.of(1, 2, 7, 8), result.get(2));
        assertEquals(Set.of(3, 4, 5), result.get(3));
        assertEquals(Set.of(6), result.get(1));
    }


}