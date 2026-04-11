package com.baeldung.lnj;

import com.baeldung.lnj.domain.model.Task;
import com.baeldung.lnj.domain.model.TaskValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NewJavaFeaturesUnitTest {

    @Test
    void whenOptionalHasValue_thenIfPresentOrElseExecutesAction() {
        var name = Optional.of("Baeldung");
        var result = new AtomicReference<String>();

        name.ifPresentOrElse(
                val -> result.set(val),
                () -> result.set("Empty")
        );

        assertEquals("Baeldung", result.get());
    }

    @Test
    void whenOptionalIsEmpty_thenOrProvidesAlternative() {
        var empty = Optional.empty();
        var defaultOptional = Optional.of("Default");
        var result = empty.or(() -> defaultOptional);

        assertEquals(defaultOptional, result);
    }

    @Test void whenOptionalIsEmpty_thenOrElseThrowThrowsException() {
        var empty = Optional.empty();

        assertThrows(
                java.util.NoSuchElementException.class,
                () -> empty.orElseThrow()
        );

        // Can also supply the custom exception we want to throw
    }

    @Test
    void whenUsingInterfacePrivateMethod_thenDefaultMethodsWork() {
        var task = new Task("task-1", "", null, null);
        TaskValidator validator = () -> task;
        assertFalse(validator.isDescriptionValid());
        assertFalse(validator.isValidCode());
    }

    @Test
    void whenGettingCurrentProcess_thenProcessHandleReturnsInfo() {
        var currentProcess = ProcessHandle.current();
        long pid = currentProcess.pid();
        var info = currentProcess.info();

        assertTrue(pid > 0);
        assertTrue(info.command().isPresent());
        assertTrue(info.startInstant().isPresent());
        assertTrue(info.totalCpuDuration().isPresent());
    }

    @Test
    void whenUsingJava11StringMethods_thenBehaviorIsCorrect() {
        assertTrue("".isBlank());
        assertTrue(" ".isBlank());
        assertFalse("Baeldung".isBlank());

        String multiLine = "First\nSecond\nThird";
        long lineCount = multiLine.lines().count();
        assertEquals(3, lineCount);

        String unicodeSpace = "\u2005 \t Hello \n";
        assertEquals("Hello", unicodeSpace.strip()); // trim() wouldn't work here
        assertEquals("Hello \n", unicodeSpace.stripLeading());
        assertEquals("\u2005 \t Hello", unicodeSpace.stripTrailing());
        assertEquals("ababab", "ab".repeat(3));
    }

    // Using @TempDir JUnit5 feature
    @Test
    void whenUsingReadAndWriteString_thenFileIsHandled(@TempDir Path tempDir) throws IOException {
        Path filePath = tempDir.resolve("testfile.txt");
        Files.writeString(filePath, "Hello Baeldung");
        String content = Files.readString(filePath);

        assertEquals("Hello Baeldung", content);
    }

    @Test
    void whenUsingPredicateNot_thenFiltersCorrectly() {
        var names = List.of("Baeldung", " ", "Java");
        var nonEmptyNames = names.stream()
                .filter(Predicate.not(String::isBlank))
                .collect(Collectors.toList());

        assertEquals(2, nonEmptyNames.size());
        assertTrue(nonEmptyNames.contains("Baeldung"));
        assertTrue(nonEmptyNames.contains("Java"));
    }

}