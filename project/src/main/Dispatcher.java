package main;

import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.declaration.CtElement;
import spoon.support.reflect.code.CtForEachImpl;
import spoon.support.reflect.code.CtForImpl;
import spoon.support.reflect.code.CtIfImpl;
import spoon.support.reflect.code.CtWhileImpl;
import spoon.support.reflect.declaration.CtClassImpl;
import spoon.support.reflect.declaration.CtMethodImpl;
import worker.Result;
import worker.WorkerFactory;
import util.Logger;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Dispatcher implements Runnable {
    String spoonTarget;
    Configuration configuration;
    Logger logger;
    ExecutorService threadPool;
    SpoonAPI spoon;
    ArrayList<Future<Result>> results = new ArrayList<>();

    public Dispatcher(String args[]) throws NonExistentFileException {
        if (! Files.exists(Paths.get(args[0])) )
            throw new NonExistentFileException();

        // save the SPOON target Folder
        spoonTarget = args[0];

        //if there are 2 arguments load config from JSON file, else use default configuration
        configuration = (args.length >= 2) ? Configuration.loadConfiguration(args[1]) : new Configuration();

        // create a new logger
        // TODO: maybe allow configuration to specify a log file where we should output the logs, code is commented in Logger
        logger = new Logger(this);

        logger.print(configuration.toString());

        // initialize the threadpool according to the configuration
        threadPool = Executors.newFixedThreadPool(configuration.global.numberOfThreads);

    }

    /**
     * try to build a SPOON model into spoon, will display errors upon failure
     */
    public void readSpoon() {
        spoon = new Launcher();
        try {
            spoon.addInputResource(spoonTarget);
            spoon.buildModel();
        } catch (spoon.compiler.ModelBuildingException e) {
            logger.print("Failed to build spoon model. Possible causes:\n File makes include of non existent classes. Use the parent folder as program argument to fix.");
        } catch (spoon.SpoonException e) {
            logger.print("Failed to build spoon model. Possible causes:\n The given path (" + spoonTarget + ") does not exist.");
        }
    }

    /**
     * Code that performs high level task delegation from the spoon model
     */
    @Override
    public void run() {
        // get a list of the features
        final ArrayList<WorkerFactory> activeDynamicWorkerFactories = configuration.getActiveDynamicFeatures();

        //Simple metrics just to test
        Integer numClasses = 0;
        Integer numMethods = 0;
        Integer numIfs = 0;
        Integer numCycles = 0; //Either while cycles or for cycles

        // Null filter -> Gets all the model elements -> can obviously be optimized
        List<CtElement> modelElements = spoon.getModel().getElements(null);

        for (CtElement element : modelElements) {
            // if there is a Worker for this thread than add it to thre results list
            for (WorkerFactory factory : activeDynamicWorkerFactories) //TODO: is there anyway to use a HashMap here, for speed?
                if (factory.matches(element))
                    results.add(threadPool.submit(factory.getWorker(element)));


            // Printing the elements being parsed and to better understand the correspondent classes -> COMMENT FOR CLEAN OUTPUT
            // logger.print(element.getClass().toString() + " --- " + element.toString());

            // Get static statistics
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
        logger.print("Analysis result of the files available at path '" + spoonTarget + "':");
        logger.print(
                "Found " + numMethods + " classe(s);\n" +
                        "Found " + numClasses + " method(s);\n" +
                        "Found " + numIfs + " IF conditional(s);\n" +
                        "Found " + numCycles + " cycle(s) (e.g. while, for or foreach);"
        );

        parseResults();
    }

    public void parseResults(){
        for (Future<Result> futureResult: results) {
            if (futureResult.isDone()) {
                try {
                    Result r = futureResult.get();
                    logger.print(r.toString());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * Updatable method that return the format in which command line arguments should be given
     *
     * @return the format in which command line arguments should be given
     */
    public static String getUsage() {
        return "<filename|foldername> [<userSettings.json>]";
    }
}
