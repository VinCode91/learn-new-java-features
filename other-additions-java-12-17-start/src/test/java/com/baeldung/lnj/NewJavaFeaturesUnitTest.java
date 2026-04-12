package com.baeldung.lnj;

import com.baeldung.lnj.domain.model.Campaign;
import com.baeldung.lnj.domain.model.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.random.RandomGenerator;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NewJavaFeaturesUnitTest {

    // indent() appends a newline at the end of the string
    @Test
    void whenUsingJava12StringIndent_thenBehaviorIsCorrect() {
        String indented = "Hello".indent(4);
        assertEquals("    Hello\n", indented);

        String multiLineStrIndented = """
        Hello
        Baeldung""".indent(4);
        assertEquals("    Hello\n    Baeldung\n", multiLineStrIndented);

        String negIntended = "    Hello".indent(-4);
        assertEquals("Hello\n", negIntended);
    }

    @Test
    void whenUsingJava12StringTransform_thenBehaviorIsCorrect() {
        String transformed = "Baeldung".transform(s -> s + " Task").transform(String::toUpperCase);
        assertEquals("BAELDUNG TASK", transformed);

        int parsed = " 80 8  0"
                .transform(s -> s.replace(" ", ""))
                .transform(Integer::parseInt);
        assertEquals(8080, parsed);
    }

    @Test
    void whenUsingJava12FileMismatch_thenFindsFirstDifference(@TempDir Path tempDir) throws IOException {
        Path file1 = tempDir.resolve("file1.txt");
        Path file2 = tempDir.resolve("file2.txt");
        Path file3 = tempDir.resolve("file3.txt");

        Files.writeString(file1, "Java 12");
        Files.writeString(file2, "Java 12");
        Files.writeString(file3, "Java 11");

        assertEquals(-1, Files.mismatch(file1, file2)); // returns -1 when files are identical

        assertEquals(6, Files.mismatch(file1, file3)); // returns index of first differing byte starting with 0
    }

    @Test
    void whenUsingJava12CompactNumberFormat_thenFormatsCorrectly() {
        NumberFormat shortFormat = NumberFormat.getCompactNumberInstance(
                Locale.US, NumberFormat.Style.SHORT);
        shortFormat.setMaximumFractionDigits(1);

        assertEquals("1K", shortFormat.format(1000));
        assertEquals("1.5M", shortFormat.format(1500000));

        NumberFormat longFormat = NumberFormat.getCompactNumberInstance(
                Locale.US, NumberFormat.Style.LONG);
        longFormat.setMaximumFractionDigits(0);

        assertEquals("1 million", longFormat.format(1000000));
        assertEquals("1 million", longFormat.format(1400000));
        assertEquals("2 million", longFormat.format(1600000));

        longFormat.setMaximumFractionDigits(2);
        assertEquals("1.69 million", longFormat.format(1687000));

        NumberFormat germanFormat = NumberFormat.getCompactNumberInstance(Locale.GERMAN, NumberFormat.Style.LONG);
        assertEquals("2 Millionen", germanFormat.format(2000000));
        assertEquals("1 Million", germanFormat.format(1000000));

        NumberFormat frenchFormat = NumberFormat.getCompactNumberInstance(Locale.FRENCH, NumberFormat.Style.LONG);
        assertEquals("2 millions", frenchFormat.format(2000000));
        assertEquals("1 million", frenchFormat.format(1000000));
    }

    // Useful NullPointerException error introduced in Java 14 which clearly reports which object is null
    @Test
    void whenNpeIsThrown_thenMessageIsHelpful() {
        Campaign campaign = null;
        Task task = new Task("t1", "Task 1", "Desc", null);

        Exception exception = assertThrows(NullPointerException.class, () -> {
            campaign.getTasks().add(task);
        });

        assertEquals("Cannot invoke \"com.baeldung.lnj.domain.model.Campaign.getTasks()\" because \"campaign\" is null",
                exception.getMessage());
    }

    /**
     * New interface RandomGenerator introduced in Java 17 to make it easier to use various PRNG algorithms
     * interchangeably and to better support stream-based programming by providing streams of
     * PRNG (pseudo-random number generators) objects.
     *
     * Existing legacy classes, such as java.util.Random and java.util.concurrent.ThreadLocalRandom,
     * now implement the RandomGenerator interface, making it compatible with the new AP
     */
    @Test
    void whenUsingRandomGenerator_thenGeneratesNumbers() {
        RandomGenerator random = RandomGenerator.getDefault();

        IntStream intStream = random.ints(5);
        long count = intStream.count();

        assertEquals(5, count);

        int value = random.nextInt(10, 20);

        assertTrue(value >= 10 && value < 20);
    }

    /**
     * jpackage was standardized in Java 16 to bundle app JAR file with the necessary parts of the JRE
     * A command-line utility for creating native, self-contained application bundles (like .msi, .dmg or .deb depending on OS)
     *
     * Typical jpackage command:
     *
     * jpackage --name my-app --input target/ --main-jar my-app.jar --main-class com.baeldung.App
     */

    /**
     * Java 17 restored floating-point operations to be consistently strict by default. Before this change, floating-point
     * semantics were sometimes subtly different depending on whether the strictfp modifier was used. With this change,
     * these differences are removed, and the language and JVM now enforce uniform floating-point semantics. This
     * consistency is especially important for applications where high precision and predictable results are critical,
     * such as scientific computing or financial calculations
     */
}