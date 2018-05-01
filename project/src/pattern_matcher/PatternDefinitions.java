package pattern_matcher;


import spoon.Launcher;

import spoon.reflect.code.CtBlock;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.support.reflect.declaration.CtClassImpl;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

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

        //System.out.println(launcher.getModel().);
        List <CtClassImpl> classElement = launcher.getModel().getElements(ctElement -> (ctElement.getClass() == CtClassImpl.class));

        if (classElement.size() > 1) {
            System.err.println("There can only be the main class defined in the Patterns file.");
        }

        Set<CtMethod> methods = classElement.get(0).getMethods();
        // TODO -- Miguel required stuff with methods

        for (CtMethod method: methods) {
            CtBlock methodBody = method.getBody();
            System.out.println(methodBody.getStatement(0).getClass() + " ---- " + methodBody);
            patterns.put(methodBody.getStatement(0).getClass(), methodBody);
        }

        System.out.println(patterns.values().toString());
        patterns.clear();
    }

    public Map<Class<?>, CtElement> getPatterns() {
        return patterns;
    }
}
