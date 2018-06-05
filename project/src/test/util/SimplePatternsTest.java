package util;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static util.Utils.runPatterns;

public class SimplePatternsTest {

    @Test
    void simplestTest() {
        HashMap<String, Integer> matches = runPatterns(
                "./src/test/testclasses/patterns/SimplePatterns.java",
                "./src/test/testclasses/patterns/SimpleTest1.java");

        assertEquals(matches.size(), 4);
        // Every Pattern is matched exactly once
        assertEquals((int) matches.get("test1"), 1);
        assertEquals((int) matches.get("test2"), 1);
        assertEquals((int) matches.get("test3"), 1);
        assertEquals((int) matches.get("test4"), 1);
    }

    @Test
    void combinationOfPatterns() {
        HashMap<String, Integer> matches = runPatterns(
                "./src/test/testclasses/patterns/SimplePatterns.java",
                "./src/test/testclasses/patterns/SimpleTest2.java");

        // There will be 4 matches, because the 4 types of nodes that trigger the matching attempts are presented in the code
        assertEquals(matches.size(), 4);
        // Frequency of each pattern
        assertEquals((int) matches.get("test1"), 3);
        assertEquals((int) matches.get("test2"), 2);
        assertEquals((int) matches.get("test3"), 1);
        assertEquals((int) matches.get("test4"), 0);
    }

    @Test
    void WithVars() {
        HashMap<String, Integer> matches = runPatterns(
                "./src/test/testclasses/patterns/VarsPatterns.java",
                "./src/test/testclasses/patterns/VarsTest.java");

        assertEquals(matches.size(), 5);
        // Frequency of each pattern
        // In lines 33
        assertEquals((int) matches.get("test1"), 1);
        // In lines 9, 10, 47 and 53
        assertEquals((int) matches.get("test2"), 4);
        // In line 18
        assertEquals((int) matches.get("test3"), 1);
        // In line 36 and 39
        assertEquals((int) matches.get("test4"), 2);
        // In line 47 and 51
        assertEquals((int) matches.get("test5"), 2);
    }

}
