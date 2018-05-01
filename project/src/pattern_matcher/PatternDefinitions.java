package pattern_matcher;


import spoon.Launcher;

import spoon.reflect.declaration.CtElement;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
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

        Launcher launcher = new Launcher();
        launcher.addInputResource(targetDefinitions); //Analyze the patterns file
        launcher.getEnvironment().setNoClasspath(true); // Semantic analysis kinda off
        launcher.buildModel();

        List<CtElement> modelElements = launcher.getModel().getElements(null);
        for (CtElement element : modelElements) {
            System.out.println(element.getClass().toString() + " --- " + element.toString());
        }
    }

    public Map<Class<?>, CtElement> getPatterns() {
        return patterns;
    }
}
