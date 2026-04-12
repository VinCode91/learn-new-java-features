package com.baeldung.lnj;

import com.baeldung.lnj.domain.model.TaskStatus;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NewJavaFeaturesUnitTest {

    @Test
    void whenStackingCaseLabels_thenOldSwitchGroupsStatuses() {
        TaskStatus status = TaskStatus.ON_HOLD;
        String type;

        switch (status) {
            case TO_DO:
            case IN_PROGRESS: type = "Active";  break;
            case ON_HOLD:
            case DONE:        type = "Inactive"; break;
            default:          type = "Unknown"; break;
        }

        assertEquals("Inactive", type);
    }

    @Test
    void whenSwitchingWithExpression_thenGroupsStatuses() {
        TaskStatus status = TaskStatus.ON_HOLD;

        var type = switch (status) {
            case TO_DO, IN_PROGRESS -> "Active";
            case ON_HOLD, DONE -> "Inactive";
            //default -> "Unknown";
        };

        assertEquals("Inactive", type);
    }

    @Test
    void whenUsingArrowSyntaxInStatement_thenActionsAreIsolated() {
        List<String> audit = new ArrayList<>();
        TaskStatus status = TaskStatus.DONE;

        switch (status) {
            case TO_DO, IN_PROGRESS -> audit.add("Active workflow");
            case ON_HOLD -> audit.add("Paused workflow");
            case DONE -> audit.add("Completed workflow");
            //default -> audit.add("Unknown workflow");
        }

        assertEquals(List.of("Completed workflow"), audit);
    }

    @Test
    void whenUsingBlockInSwitchExpression_thenYieldProvidesBranchValue() {
        List<String> audit = new ArrayList<>();
        TaskStatus status = TaskStatus.DONE;

        String label = switch (status) {
            case DONE -> {
                audit.add("Completed workflow");
                String result = "Task is complete";
                // return; // not valid: triggers a compilation error
                yield result;
            }
            default -> throw new RuntimeException("Can't handle this status"); // default branch necessary here since all statuses aren't handled
        };

        assertEquals("Task is complete", label);
        assertEquals(List.of("Completed workflow"), audit);
    }
}