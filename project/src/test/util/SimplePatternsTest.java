package util;

import main.Configuration;
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

import static org.junit.jupiter.api.Assertions.fail;

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
    void test1() {
        HashMap<String, Integer> macthes = runPatterns(
                "./src/test/testclasses/patterns/simplePatterns.java",
                "./src/test/testclasses/patterns/simpleTest1.java");
    }

}
