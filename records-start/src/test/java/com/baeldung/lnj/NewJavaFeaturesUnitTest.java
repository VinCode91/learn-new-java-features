package com.baeldung.lnj;

import com.baeldung.lnj.domain.model.Campaign;
import com.baeldung.lnj.domain.model.Task;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NewJavaFeaturesUnitTest {

    @Test
    void givenATask_whenCreatingRecord_thenFieldsAreAccessible() {

        Task task1 = new Task("task-1", "Test Record");
        Task task2 = new Task("task-1", "Test Record");
        Task task3 = new Task("task-2", "Another Record");

        assertEquals("task-1", task1.code());
        assertEquals("Test Record", task1.name());

        assertEquals(task1, task2, "Tasks with same values should be equal");
        assertNotEquals(task1, task3, "Tasks with different values should not be equal");

        assertEquals(task1.hashCode(), task2.hashCode());
        assertNotEquals(task1.hashCode(), task3.hashCode());

        assertTrue(task1.toString().contains("code=task-1"));
        assertTrue(task1.toString().contains("name=Test Record"));

        assertThrows(IllegalArgumentException.class, () -> new Task(" "));
    }

    /**
     * Local records can be useful to operate on temporary object tuples (n-uplet d'objets en français)
     * especially when handled by sreams
     */
    @Test
    void givenATask_whenUsingLocalRecord_thenCanBeDefinedInMethod() {

        record CampaignAndTask(Campaign campaign, Task task) {
            int combinedNameLength() {
                return campaign.getName().length() + task.name().length();
            }
        }

        Campaign campaign = new Campaign("c1", "Campaign 1", "Desc");
        Task task1 = new Task("t1", "Task 1");
        Task task2 = new Task("t2", "Task 2 is longer");

        List<Task> tasks = List.of(task1, task2);

        CampaignAndTask longestPair = tasks.stream()
                .map(task -> new CampaignAndTask(campaign, task))
                .max(Comparator.comparing(CampaignAndTask::combinedNameLength))
                .orElseThrow();

        assertEquals("Task 2 is longer", longestPair.task().name());
    }
}
