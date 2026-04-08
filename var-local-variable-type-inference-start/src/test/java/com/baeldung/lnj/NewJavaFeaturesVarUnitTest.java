package com.baeldung.lnj;

import com.baeldung.lnj.domain.model.Task;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class NewJavaFeaturesVarUnitTest {

    @Test
    void whenUsingVarWithSimpleTypes_thenCompilerInfersConcreteType() {
        var name = "Baeldung";
        var age = 30;
        var newAge = age + 5;

        assertEquals("Baeldung", name);
        assertEquals(30, age);
        assertEquals(35, newAge);
        assertTrue(name instanceof String);
    }

    @Test
    void whenUsingVarWithDomainType_thenTypeIsTask() {
        var task = new Task(
                "task-1",
                "Learn var",
                "Learn local variable type inference",
                null
        );

        assertEquals("task-1", task.getCode());
        assertTrue(task instanceof Task);
    }

    @Test
    void whenDeclaringVarWithoutInitializer_thenCodeDoesNotCompile() {
        // valid
        var name = "Baeldung";

        assertEquals("Baeldung", name);

        // invalid  must be initialized in the same declaration
        // var uninitialized;
    }

    @Test
    void whenInitializingVarWithNullOnly_thenCodeDoesNotCompile() {
        // valid
        var description = "Local variable type inference";

        assertEquals("Local variable type inference", description);

        // invalid  null alone does not provide a concrete type
        // var onlyNull = null;
    }

    @Test
    void whenChangingVarToIncompatibleType_thenCodeDoesNotCompile() {
        // valid
        var name = "Baeldung";
        var price = 40.5; // double

        // invalid  inferred type String and int is not compatible with String
        // name = 42;
        price = 40; // still double

        // The infered type remains no matter what
        price = (int) price; // still double
        var intPrice = (int) price; // int
    }

    @Test
    void whenUsingVarOutsideLocalVariables_thenCodeDoesNotCompile() {
        class VarMisuseExample {
            // invalid as a field
            // var fieldLevel = "field";

            // invalid as a method parameter
            // void method(var param) {}

            // invalid as a return type
            // var brokenMethod() {
            //     return 1;
            // }
        }
    }

    @Test
    void whenInferringFromNumericLiterals_thenTypesFollowStandardRules() {
        var count = 1; // int
        var big = 1L; // long
        var precise = 1.0; // double
        var small = 1.0f; // float

        Object boxedCount = count;
        Object boxedBig = big;
        Object boxedPrecise = precise;
        Object boxedSmall = small;

        assertEquals(Integer.class, boxedCount.getClass());
        assertEquals(Long.class, boxedBig.getClass());
        assertEquals(Double.class, boxedPrecise.getClass());
        assertEquals(Float.class, boxedSmall.getClass());
    }

    @Test
    void whenInferringFromConditionalExpression_thenTypeIsCommonSupertype() {
        boolean useString = true;
        var text = useString
                ? "Baeldung"
                : new StringBuilder("Baeldung"); // Expression has an intersection type

        Serializable s = text;
        Comparable c = text;
        CharSequence cs = text;
        // Appendable a = text; // invalid: Appendable interface belongs ONLY to StringBuilder
        assertEquals(8, text.length());

        // append() is defined in StringBuilder but not in CharSequence
        // text.append(" tutorial"); // doesn't compile
    }

    @Test
    void whenUsingVarWithArrays_thenInitializerMustProvideArrayType() {
        var numbers = new int[] { 1, 2, 3 };

        assertEquals(3, numbers.length);
        assertEquals(1, numbers[0]);

        // invalid: missing new and explicit type
        // var moreNumbers = { 1, 2, 3 };

        // This also doesn't work
        // var[] moreIntegers = new int[] {1, 2, 3};
    }

    private List<Task> createTasks() {
        var task = new Task("task", "name", "description", null);
        var result = new LinkedList<Task>();
        result.add(task);
        return result;
    }

    @Test
    void whenInferringFromMethodCall_thenTypeFollowsReturnType() {
        var tasks = createTasks(); // List<Task>
        tasks.add(new Task("task-2", "Second", "Second task", null));

        // doesn't compile because addFirst() is not declared on List even though it is a LinkedList method
        // tasks.addFirst(new Task("task-3", "Third", "Third task", null));

        assertEquals(2, tasks.size());
        assertTrue(tasks instanceof LinkedList);
    }

    /**
     * If we absolutely need tasks to be of type List<Task>, we can't use var since it requires
     * a concrete List class for initialization which is then inferred by the compiler
     */
    @Test
    void whenUsingVarWithGenericsAndDiamond_thenConcreteTypesAreInferred() {
        var tasks = new ArrayList<Task>();
        var task = new Task("task-1", "Learn var", "description", null);
        tasks.add(task);

        var mixed = new ArrayList<>();
        mixed.add("First value");
        mixed.add(42);

        // then
        assertEquals(ArrayList.class, tasks.getClass());
        assertEquals(1, tasks.size());
        assertEquals(2, mixed.size());
    }

    @Test
    void whenUsingVarInEnhancedForLoop_thenElementTypeIsInferred() {
        var tasks = List.of(
                new Task("task-1", "First", "description", null),
                new Task("task-2", "Second", "description", null)
        );

        var codes = new ArrayList<String>();
        for (var task : tasks) {
            codes.add(task.getCode());
        }

        assertEquals(List.of("task-1", "task-2"), codes);
    }

    @Test
    void whenUsingVarInTryWithResources_thenResourceTypeIsInferred() {
        var codes = List.of("TASK-1", "TASK-2");

        try (var output = new ByteArrayOutputStream()) {
            for (var code : codes) {
                output.write(code.getBytes(StandardCharsets.UTF_8));
            }

            assertTrue(output.size() > 0);
        }
        // invalid: var is not allowed here
        // catch (var ex) {
        catch (IOException ex) {
            fail("Unexpected IO exception");
        }
    }

    @Test
    void whenUsingVarInLambdaExpressions_thenTargetTypeMustBeExplicit() {
        Predicate<String> longEnough = s -> s.length() > 10;
        Predicate<String> longEnoughWithVar = (var s) -> s.length() > 10;

        // invalid: lambda expressions need an explicit target type
        // var longEnough2 = s -> s.length() > 10;
        // var longEnoughWithVar2 = (var s) -> s.length() > 10;

        assertTrue(longEnough.test("a very long string"));
        assertTrue(longEnoughWithVar.test("a very long string"));
    }

    /**
     * var in lambda parameters becomes most useful when we want to add annotations to the parameters while still
     * relying on type inference
     */
    @interface NotNull {}

    @Test
    void whenUsingVarInLambdaParametersWithAnnotations_thenInferenceStillApplies() {
        Function<String, String> processor =
                (@NotNull var s) -> s.toUpperCase();

        var result = processor.apply("baeldung");

        assertEquals("BAELDUNG", result);
    }

    /**
     * var with Anonymous class
     */
    @Test
    void givenAnonymousClass_whenUsingVar_thenExtraMembersAreAccessible() {
        var moreSpecificThanRunnable = new Runnable() {
            @Override
            public void run() {
            }

            String getStatus() {
                return "OK";
            }
        };

        var status = moreSpecificThanRunnable.getStatus();

        assertEquals("OK", status);
    }
}
