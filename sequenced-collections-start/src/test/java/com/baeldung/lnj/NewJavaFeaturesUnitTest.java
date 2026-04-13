package com.baeldung.lnj;

import com.baeldung.lnj.domain.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.SequencedMap;
import java.util.SequencedSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NewJavaFeaturesUnitTest {

    private Task taskA, taskB, taskC;

    @BeforeEach
    void setUp() {
        taskA = new Task("TaskA", "Complete Report", "Finish quarterly sales report", LocalDate.of(2050, 7, 15));
        taskB = new Task("TaskB", "Schedule Meeting", "Arrange team sync-up", LocalDate.of(2050, 7, 10));
        taskC = new Task("TaskC", "Review Code", "Review pull requests for project", LocalDate.of(2050, 7, 12));
    }

    @Test
    void givenDequeOfTasks_whenUsingSequencedDeque_thenCanReverseView() {

        Deque<Task> taskDeque = new ArrayDeque<>();
        taskDeque.addLast(taskA);
        taskDeque.addLast(taskB);
        taskDeque.addLast(taskC);

        Deque<Task> reversedDeque = taskDeque.reversed();
        assertEquals(taskA, reversedDeque.getLast());
        assertEquals(taskC, reversedDeque.getFirst());
    }

    @Test
    void givenListOfTasks_whenUsingSequencedList_thenCanGetLastTaskAndReverseView() {

        List<Task> taskList = new ArrayList<>();
        taskList.add(taskA);
        taskList.add(taskB);
        taskList.add(taskC);

        assertEquals(taskC, taskList.getLast());
        assertEquals(taskC, taskList.reversed().getFirst());
    }

    @Test
    void givenSetOfTasks_whenUsingSequencedSet_thenGetLastTaskIsConsistent() {

        SequencedSet<Task> taskSet = new LinkedHashSet<>();
        taskSet.add(taskA);
        taskSet.add(taskB);
        taskSet.add(taskC);

        assertEquals(taskC, taskSet.getLast());
    }

    /**
     * {@link java.util.SequencedMap} does nor inherit from {@link java.util.SequencedCollection}
     * but the encounter ordering applies to key-value mappings instead of individual elements
     */
    @Test
    void givenMapOfTasks_whenUsingSequencedMap_thenCanAccessFirstAndLastEntries() {
        SequencedMap<String, Task> taskMap = new LinkedHashMap<>();
        taskMap.put("TaskA", taskA);
        taskMap.put("TaskB", taskB);
        taskMap.put("TaskC", taskC);

        assertEquals("TaskA", taskMap.firstEntry().getKey());
        assertEquals("TaskC", taskMap.lastEntry().getKey());
    }
}