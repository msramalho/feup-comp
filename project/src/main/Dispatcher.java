package main;

import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.code.CtForEachImpl;
import spoon.support.reflect.code.CtForImpl;
import spoon.support.reflect.code.CtIfImpl;
import spoon.support.reflect.code.CtWhileImpl;
import spoon.support.reflect.declaration.CtClassImpl;
import spoon.support.reflect.declaration.CtMethodImpl;
import util.Report;
import worker.WorkerFactory;
import util.Logger;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Dispatcher implements Runnable {
    String spoonTarget;
    Configuration configuration;
    Logger logger = new Logger(this);
    ExecutorService threadPool;
    SpoonAPI spoon;
    ArrayList<Future<Report>> results = new ArrayList<>();

    public Dispatcher(String args[]) throws NonExistentFileException {
        if (args.length == 0 || !Files.exists(Paths.get(args[0])))
            throw new NonExistentFileException();

        // save the SPOON target Folder
        spoonTarget = args[0];

        //if there are 2 arguments load config from JSON file, else use default configuration
        configuration = (args.length >= 2) ? Configuration.loadConfiguration(args[1]) : new Configuration();

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
            logger.print("Failed to build spoon model. Possible causes:\nFile makes include of non existent classes. Use the parent folder as program argument to fix.");
        } catch (spoon.SpoonException e) {
            logger.print("Failed to build spoon model. Possible causes:\nThe given path (" + spoonTarget + ") does not exist.");
        }
    }

    /**
     * Code that performs high level task delegation from the spoon model
     */
    @Override
    public void run() {
        // get a list of the features
        final List<WorkerFactory> workerFactories = configuration.getActiveDynamicFeatures();

        Collection<CtPackage> packages = spoon.getModel().getAllPackages();
        for (CtPackage ctPackage : packages) {
            handlePackage(workerFactories, ctPackage);
        }
        //        ClassScanner nodeManager = new ClassScanner(threadPool, workerFactories, spoon.getModel().getRootPackage());
        //        nodeManager.run();
    }

    private void handlePackage(List<WorkerFactory> workerFactories, CtPackage ctPackage) {
        logger.print("Package: " + ctPackage.getQualifiedName());

        for (CtType ctType : ctPackage.getTypes()) {
            // TODO do something with Future's result
            threadPool.submit(new ClassScanner(threadPool, workerFactories, ctType));
        }
    }

    public void parseResults() {
        for (Future<Report> futureResult : results) {
            if (futureResult.isDone()) {
                try {
                    Report r = futureResult.get();
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
