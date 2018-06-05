package util;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.Utils.runPatterns;

public class PatternsWithAnyTest {

    @Test
    void simpleAnys() {
        HashMap<String, Integer> matches = runPatterns(
                "./src/test/testclasses/patterns/AnyPatterns.java",
                "./src/test/testclasses/patterns/AnyTest1.java");

        assertEquals(matches.size(), 3);
        // Frequency of each pattern
        // despite being lazy the pattern will try to get the match even if it implies consuming more than the first possible match
        assertEquals((int) matches.get("test1"), 1);
        assertEquals((int) matches.get("test2"), 1);
        // Since the approach method is not defined, greedy or lazy, it fails to consume
        assertEquals((int) matches.get("test3"), 0);
    }

    @Test
    void minAnys() {
        HashMap<String, Integer> matches = runPatterns(
                "./src/test/testclasses/patterns/AnyPatterns.java",
                "./src/test/testclasses/patterns/AnyTest2.java");

        assertEquals(matches.size(), 4);
        // Frequency of each pattern
        // Line 17
        //assertEquals((int) matches.get("min1"), 1);
        // Lines 12 and 17
        assertEquals((int) matches.get("min2"), 2);
        // Lines 8, 12 and 17
        assertEquals((int) matches.get("min3"), 3);
    }
}
