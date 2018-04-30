package pattern_matcher;


import spoon.reflect.declaration.CtElement;
import spoon.support.compiler.SnippetCompilationHelper;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class PatternDefinitions {
    private Map<Class<?>, CtElement> patterns;

    public PatternDefinitions(String targetDefinitions) throws FileNotFoundException {
        if (!Files.exists(Paths.get(targetDefinitions)))
            throw new FileNotFoundException(targetDefinitions + " does not exist.");

        patterns = new HashMap<>();

        processPatternDefinitions(targetDefinitions);
    }

    private void processPatternDefinitions(String targetDefinitions) {
//        SnippetCompilationHelper.compileStatement()
    }

    public Map<Class<?>, CtElement> getPatterns() {
        return patterns;
    }
}
