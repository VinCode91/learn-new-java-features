package com.baeldung.lnj;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baeldung.lnj.domain.model.Campaign;
import com.baeldung.lnj.domain.model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NewJavaFeaturesUnitTest {

    private static Campaign aTestCampaign(Task... tasks) {
        Campaign campaign = new Campaign("code", "name", "desc");
        campaign.setTasks(Set.of(tasks));
        return campaign;
    }

    private static Task aTestTask(String taskCode) {
        return new Task(taskCode, "task name", "task desc", LocalDate.now());
    }

    @Test
    void whenUsingMapMulti_thenExpandsElements() {
        // given
        Campaign campaign1 = aTestCampaign(aTestTask("t1"), aTestTask("t2"));
        Campaign campaign2 = aTestCampaign(aTestTask("t3"), aTestTask("t4"));
        campaign2.setClosed(true);

        // when

        /// using filter/flatMap pattern
        Stream<Task> tasks = Stream.of(campaign1, campaign2)
                .filter(campaign -> !campaign.isClosed())
                .flatMap(campaign -> campaign.getTasks().stream());

        // Above code is "seemingly" equivalent to below code which highlights performance issues since we are creating a Stream for
        // all campaigns even closed ones

//        Stream<Task> tasks = Stream.of(campaign1, campaign2)
//                .flatMap(campaign -> {
//                    if (!campaign.isClosed()) {
//                        return campaign.getTasks().stream();
//                    }
//                    return Stream.empty();
//                });

        Stream<Task> tasks2 = Stream.of(campaign1, campaign2)
                .mapMulti((campaign, downstream) -> {
                    if (!campaign.isClosed()) {
                        campaign.getTasks().forEach(downstream::accept);
                    }
                });

        // Instead of returning a new Stream for each element as with flatMap, mapMulti() gives a Consumer
        // representing the downstream pipeline. downstream.accept() is only called when needed.
        // Much more flexible alternative to filter/map in complex scenarios where various checks and/or data transformations may be needed

        Stream<String> taskCodes = Stream.of(campaign1, campaign2)
                .mapMulti((campaign, downstream) -> {
                    if (!campaign.isClosed()) {
                        campaign.getTasks().forEach(t -> downstream.accept(t.getCode()));
                    }
                });

        // then
        assertEquals(2, tasks.count());
        assertEquals(2, tasks2.count());
        assertEquals(Set.of("t1", "t2"), taskCodes.collect(Collectors.toSet()));
    }

    @Test
    void whenUsingStreamToList_thenCreatesUnmodifiableList() {
        // given
        List<Task> tasks = List.of(aTestTask("t1"), aTestTask("t2"), aTestTask("t3"));

        // when
        List<Task> unmodifiableList = tasks.stream().toList(); // shorthand for collect(Collectors.toUnmodifiableList()), which was introduced in Java 10

        // then
        assertEquals(3, unmodifiableList.size());
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableList.remove(1));
    }

    @Test
    void whenUsingTeeingCollector_thenMergesTwoResults() {
        // given
        List<Task> tasks = List.of(aTestTask("t1"), aTestTask("t2"), aTestTask("t3"));

        // when
        String result = tasks.stream()
                .map(Task::getCode)
                // encapsulate 2 collectors and combine their results.
                // You may even use teeing with either encapsulated collector if more operations are needed.
                // Applying either collector directly would consume the Stream, which we don't want
                .collect(Collectors.teeing(
                        Collectors.counting(),
                        Collectors.joining(", "),
                        (count, codes) -> "There are %s tasks to complete: %s".formatted(count, codes)
                ));

        // then
        assertEquals("There are 3 tasks to complete: t1, t2, t3", result);
    }

}