package main;

import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.declaration.CtElement;
import spoon.support.reflect.code.CtForEachImpl;
import spoon.support.reflect.declaration.CtClassImpl;
import spoon.support.reflect.declaration.CtMethodImpl;
import spoon.support.reflect.code.CtIfImpl;
import spoon.support.reflect.code.CtForImpl;
import spoon.support.reflect.code.CtWhileImpl;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        // Check if command line arguments are in a valid format
        if (args.length < 1 || args.length > 2) {
            System.err.println("Incorrect arguments, usage: <filename|foldername> [<userSettings.json>]");
            return;
        }

        //Load the configurations from the default path or from user specified path
        Configuration configuration;
        if (args.length == 2) {
            configuration = Configuration.loadConfiguration(args[1]);
        } else {
            configuration = new Configuration();
        }

        System.out.println(configuration);
        ExecutorService threadPool = Executors.newFixedThreadPool(configuration.global.numberOfThreads);


        SpoonAPI spoon = new Launcher();
        try {
            spoon.addInputResource(args[0]);
            spoon.buildModel();
        } catch (spoon.compiler.ModelBuildingException e) {
            System.err.println("Failed to build spoon model. Possible causes:\n" +
                    " * File makes include of non existent classes. Use the parent folder as program argument to fix.");
            return;
        } catch (spoon.SpoonException e) {
            System.err.println("Failed to build spoon model. Possible causes:\n" +
                    " * The given path does not exist.");
            return;
        }

        //Simple metrics just to test
        Integer numClasses = 0;
        Integer numMethods = 0;
        Integer numIfs = 0;
        Integer numCycles = 0; //Either while cycles or for cycles

        // Null filter -> Gets all the model elements -> can obviously be optimized
        List<CtElement> modelElements = spoon.getModel().getElements(null);

        for (CtElement element : modelElements) {
            // Printing the elements being parsed and to better understand the correspondent classes -> COMMENT FOR CLEAN OUTPUT
            System.out.println(element.getClass().toString() + " --- " + element.toString());

            if (element.getClass().equals(CtClassImpl.class)) {
                ++numClasses;
            } else if (element.getClass().equals(CtMethodImpl.class)) {
                ++numMethods;
            } else if (element.getClass().equals(CtIfImpl.class)) {
                ++numIfs;
            } else if (element.getClass().equals(CtForImpl.class) || element.getClass().equals(CtForEachImpl.class) || element.getClass().equals(CtWhileImpl.class)) {
                ++numCycles;
            }
        }

        // Display of analyzed metrics
        System.out.println("Analysis result of the files available at path '" + args[0] + "':");
        System.out.println(
                "Found " + numMethods + " classe(s);\n" +
                        "Found " + numClasses + " method(s);\n" +
                        "Found " + numIfs + " IF conditional(s);\n" +
                        "Found " + numCycles + " cycle(s) (e.g. while, for or foreach);"
        );
    }
}
