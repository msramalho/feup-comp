package util;

import org.junit.jupiter.api.Test;
import pattern_matcher.PatternDefinitions;
import spoon.reflect.declaration.CtElement;
import spoon.support.reflect.code.CtIfImpl;
import spoon.support.reflect.code.CtInvocationImpl;
import spoon.support.reflect.code.CtLocalVariableImpl;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PatternsExtractor {

    private static PatternDefinitions extractor = null;

    @Test
    void var() {
        try {
            extractor = new PatternDefinitions("./src/test/testclasses//ExtractorTest.java");
        } catch(FileNotFoundException e) {
            fail("Tried to access file that does not exist.");
        }
        Map<Class<?>, List<CtElement>> patterns =  extractor.getPatterns();

        assertEquals(patterns.size(), 3);
        // Testing extractor content
        assertEquals(patterns.get(CtInvocationImpl.class).size(), 2);
        assertEquals(patterns.get(CtIfImpl.class).size(), 1);
        assertEquals(patterns.get(CtLocalVariableImpl.class).size(), 1);

        assertEquals(patterns.get(CtInvocationImpl.class).get(0).toString(),
                "{\n" +
                        "    java.lang.System.out.println(\"test\");\n" +
                        "}");
        assertEquals(patterns.get(CtInvocationImpl.class).get(1).toString(),
                "{\n" +
                        "    java.lang.System.out.println(\"test\");\n" +
                        "}");
        assertEquals(patterns.get(CtIfImpl.class).get(0).toString(),
                "{\n" +
                        "    if (true)\n" +
                        "        return;\n" +
                        "\n" +
                        "}");
        assertEquals(patterns.get(CtLocalVariableImpl.class).get(0).toString(),
                "{\n" +
                        "    int i = 0;\n" +
                        "}");
}

}
