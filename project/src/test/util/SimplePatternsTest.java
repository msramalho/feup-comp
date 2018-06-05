package util;

import org.junit.jupiter.api.Test;
import pattern_matcher.PatternDefinitions;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.declaration.CtElement;
import worker.DynamicWorker;
import worker.DynamicWorkerFactory;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SimplePatternsTest {

    private HashMap<String, Integer> runPatterns(String patternsFile, String sourcePath) {

        // Getting patterns from file
        PatternDefinitions patternDefinitions = null;
        try {
            patternDefinitions = new PatternDefinitions(patternsFile);
        } catch (FileNotFoundException e) {
            fail("Tried to access file that does not exist.");
        }

        // Building source code
        SpoonAPI spoon = new Launcher();
        spoon.addInputResource(sourcePath);
        try {
            spoon.buildModel();
        } catch(spoon.compiler.ModelBuildingException e) {
            fail("Failed to build spoon model. Possible reasons: Inexistent file or file with dependency of classes that were not included.");
        }

        // Matching each element from the source code, triggering patterns manually to assure that only pattern matching is tested in this set of tests
        HashMap<String, Integer> matches = new HashMap<>();
        for (CtElement element : spoon.getModel().getElements(null)) {
             List<CtElement> possiblePatterns = patternDefinitions.getPatterns().get(element.getClass());
             if (possiblePatterns == null)
                 continue;

             for (CtElement pattern : possiblePatterns) {
                 String patternName = DynamicWorkerFactory.getPatternName(pattern);
                 matches.put(patternName,
                         matches.getOrDefault(patternName, 0) + (new DynamicWorker(element, patternName, pattern)).call().getValue());
             }
        }
        return matches;
    }

    @Test
    void simplestTest() {
        HashMap<String, Integer> matches = runPatterns(
                "./src/test/testclasses/patterns/simplePatterns.java",
                "./src/test/testclasses/patterns/simpleTest1.java");

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
                "./src/test/testclasses/patterns/simplePatterns.java",
                "./src/test/testclasses/patterns/simpleTest2.java");

        // There will be fore 4 matches, because there are the 4 types of nodes that trigger the matching attempts
        assertEquals(matches.size(), 4);
        // Frequency of each pattern
        assertEquals((int) matches.get("test1"), 3);
        assertEquals((int) matches.get("test2"), 2);
        assertEquals((int) matches.get("test3"), 1);
        assertEquals((int) matches.get("test4"), 0);
    }

}
