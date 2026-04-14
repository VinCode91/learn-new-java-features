package com.baeldung.lnj;

import com.baeldung.lnj.domain.model.MyResource;
import com.baeldung.lnj.domain.model.Task;
import com.baeldung.lnj.domain.model.TaskRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NewJavaFeaturesUnitTest {

    @Test
    void whenParsingInvalidNumber_thenExceptionIsHandledSilently() {
        try {
            int result = Integer.parseInt("not-a-number");
        }
        catch (NumberFormatException _) {
            System.out.println("Failed to parse number");
        }
    }

    @Test
    void whenReplacingValues_thenKeyIsIgnored() {
        Task task = new Task("t1", "My Task", "Desc", null);
        Map<String, Task> taskMap = new HashMap<>(Map.of("t1", task));

        taskMap.replaceAll((_, t) -> new Task(t.getCode(), t.getName()
                .toUpperCase(), t.getDescription(), t.getDueDate()));

        assertEquals("MY TASK", taskMap.get("t1").getName());
    }

    @Test
    void whenUsingTryWithResources_thenResourceIsNotReferenced() {
        try (var _ = new MyResource()) {
            System.out.println("Inside try block");
        }
    }

    @Test
    void whenRemovingFirstItem_thenRemovedValueIsIgnored() {
        Queue<String> queue = new LinkedList<>();
        queue.add("first");
        queue.add("second");

        var _ = queue.poll();
        var _ = queue.poll(); // _ can be "declared" several times in same scope

        assertEquals(0, queue.size());
    }

    @Test
    void whenSwitchingOnType_thenCorrectTypeIsMatched() {
        Object obj = "Hello Baeldung";
        String result = "";

        switch (obj) {
            case Task _ -> result = "It's a Task";
            case String _ -> result = "It's a String";
            default -> result = "Other";
        }

        assertEquals("It's a String", result);
    }

    @Test
    void whenCheckingInstanceWithPattern_thenSpecificFieldIsExtracted() {
        Object taskRec = new TaskRecord("task-1", "My Record");

        if (taskRec instanceof TaskRecord(var _, var name)) {
            assertEquals("My Record", name);
        } else {
            Assertions.fail("Pattern did not match");
        }
    }
}