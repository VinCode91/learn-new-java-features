package com.baeldung.lnj;

import com.baeldung.lnj.domain.model.CategorizedTask;
import com.baeldung.lnj.domain.model.CustomStandardTask;
import com.baeldung.lnj.domain.model.FeatureTask;
import com.baeldung.lnj.domain.model.LargeFeatureTask;
import com.baeldung.lnj.domain.model.StandardTask;
import com.baeldung.lnj.domain.model.TaskResult;
import com.baeldung.lnj.domain.model.UrgentTask;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class NewJavaFeaturesUnitTest {

    @Test
    void givenNonSealedTask_whenExtended_thenCanOverrideDetails() {
        StandardTask custom = new CustomStandardTask();
        StandardTask anonymousStandardTask = new StandardTask() {
            @Override
            protected String getDetails() {
                return "anonymous";
            }
        };

        // The category is constructed using the subclasses' details
        assertEquals("standard_custom", custom.getCategory());
        assertEquals("standard_anonymous", anonymousStandardTask.getCategory());
    }

    @Test
    void whenGetPermittedSubtypesCategory_thenReturnsCorrectValue() {
        CategorizedTask urgent = new UrgentTask();
        assertEquals("urgent", urgent.getCategory());

        CategorizedTask feature = new FeatureTask();
        assertEquals("feature", feature.getCategory());

        CategorizedTask large = new LargeFeatureTask();
        assertEquals("large-feature", large.getCategory());
    }

    @Test
    void givenTaskResults_whenCallingHasError_thenCorrectResultReturned() {
        TaskResult success = new TaskResult.Success();
        assertFalse(success.hasError());

        TaskResult failure = new TaskResult.Failure();
        assertTrue(failure.hasError());
    }


    // Reflection and Sealed Classes
    @Test
    void givenSealedClass_whenCheckingIfSealed_thenReturnTrue() {
        assertTrue(CategorizedTask.class.isSealed());
    }
    @Test
    void givenNonSealedClass_whenCheckingIfSealed_thenReturnFalse() {
        assertFalse(StandardTask.class.isSealed());
    }
    @Test
    void whenGettingPermittedSubclasses_thenCorrectClassesReturned() {
        Class<?>[] permittedSubclasses = CategorizedTask.class.getPermittedSubclasses();
        assertNotNull(permittedSubclasses);
        assertEquals(3, permittedSubclasses.length);

        List<Class<?>> actualList = Arrays.asList(permittedSubclasses);
        assertTrue(actualList.contains(FeatureTask.class));
        assertTrue(actualList.contains(UrgentTask.class));
        assertTrue(actualList.contains(StandardTask.class));
    }
}
