package com.baeldung.lnj;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PatternMatchingUnitTest {

    int getLengthOld(Object obj) {
        if (obj instanceof String) {
            String s = (String) obj;
            return s.length();
        } return 0;
    }

    int getLengthNew(Object obj) {
        if (obj instanceof String s) {
            return s.length();
        } return 0;
    }

    @Test
    void whenUsingPatternMatching_thenBehavesSameAsOldWay() {
        String myString = "This is a Baeldung lesson";

        assertEquals(getLengthOld(myString), getLengthNew(myString));
    }

    @Test
    void whenUsingPatternVariable_thenScopeIsLimited() {
        Object obj = "Hello";

        if (obj instanceof String s) {
            // 's' is in scope here
            assertEquals("Hello", s);
        } else {
            // 's' is NOT in scope here
            // s.length(); // This would be a compile error
        }

        // 's' is NOT in scope here
        // s.length(); // This would be a compile error
    }

    @Test
    void whenUsingNegatedPattern_thenScopeIsCorrectInElseBlock() {
        Object obj = "Baeldung";
        int length = 0;

        if (!(obj instanceof String s)) {
            // s.length(); // COMPILE ERROR: s is not in scope here
            length = 0;
        } else {
            length = s.length(); //s IS in scope here!
        }

        assertEquals(8, length);
    }

    @Test
    void whenUsingPatternInLogicalExpression_thenScopeIsFlowSensitive() {
        Object obj = "Baeldung";

        boolean isLongString = false;
        // s.length() is only evaluated if the instanceof test succeeds
        if (obj instanceof String s && s.length() > 5) {
            isLongString = true;
            System.out.println(s.toUpperCase());
        }

        assertTrue(isLongString);
    }

    @Test
    void whenUsingPatternInWhileLoop_thenScopeIsCorrect() {
        Object[] objects = {"Baeldung", 1, "Java", 2, "Pattern"};
        List<String> strings = new ArrayList<>();
        int i = 0;

        while (i < objects.length && objects[i] instanceof String s) {
            strings.add(s);
            i++;
        }

        assertThat(strings).containsExactly("Baeldung");
    }

    @Test
    void whenUsingPatternInTernary_thenScopeIsCorrect() {
        Object obj = "Baeldung";

        int length = (obj instanceof String s) ? s.length() : 0;

        assertEquals(8, length);
    }



}