package com.baeldung.lnj;

import com.baeldung.lnj.domain.model.AuditedTask;
import com.baeldung.lnj.domain.model.ImmutableTask;
import com.baeldung.lnj.domain.model.PriorityTask;
import com.baeldung.lnj.domain.model.WebTask;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NewJavaFeaturesUnitTest {

    @Test
    void whenPriorityIsWithinValidRange_thenTaskIsCreatedSuccessfully() {
        assertDoesNotThrow(() ->
                new PriorityTask("TASK-001", "Task Name", "Task Description", LocalDate.now(), 5)
        );
    }

    @Test
    void whenPriorityIsOutsideValidRange_thenIllegalArgumentExceptionThrown() {
        String expectedExceptionMessage = "Priority must be between 0 and 10";

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new PriorityTask("TASK-002", "Task Name", "Task Description", LocalDate.now(), -1)
        );
        assertEquals(expectedExceptionMessage, exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () ->
                new PriorityTask("TASK-003", "Task Name", "Task Description", LocalDate.now(), 11)
        );
        assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    @Test
    void whenUrlAndNameRequireSanitization_thenValuesProcessedCorrectly() {
        WebTask webTask = new WebTask("TASK-004", null, "Task Description", "  baeldung.com  ");
        assertEquals("https://baeldung.com", webTask.getUrl());
        assertEquals("Untitled Web Task", webTask.getName());
    }

    @Test
    void whenInstantiatingAuditedTask_thenLogPrintedSuccessfully() {
        assertDoesNotThrow(() -> new AuditedTask("TASK-005", "Audit Task"));
    }

    @Test
    void whenInstantiatingImmutableTask_thenDefaultPriorityIsSetBasedOnTitle() {
        ImmutableTask normalTask = new ImmutableTask("Regular Task");
        assertEquals(5, normalTask.priority());

        ImmutableTask urgentTask = new ImmutableTask("Urgent Bug Fix");
        assertEquals(1, urgentTask.priority());

        assertThrows(IllegalArgumentException.class, () -> new ImmutableTask("Erroneous", -1));
    }
}