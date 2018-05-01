package main;

import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.CtType;
import util.Logger;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Dispatcher implements Runnable {
    Configuration configuration;
    Logger logger;
    ExecutorService threadPool;
    SpoonAPI spoon;

    FactoryManager factoryManager;


    public Dispatcher(Configuration config) {
        if (config == null)
            throw new NullPointerException("Configuration file is NULL.");
        this.configuration = config;
        this.logger = new Logger(this);

        threadPool = Executors.newFixedThreadPool(configuration.global.numberOfThreads);

        logger.print(configuration.toString());
    }

    public void setFactoryManager(FactoryManager factoryManager) {
        this.factoryManager = factoryManager;
    }

    /**
     * try to build a SPOON model into spoon, will display errors upon failure
     */
    public void readSpoonTarget(String spoonTarget) throws FileNotFoundException {
        if (!Files.exists(Paths.get(spoonTarget)))
            throw new FileNotFoundException(spoonTarget + " does not exist.");

        spoon = new Launcher();
        try {
            spoon.addInputResource(spoonTarget);
            spoon.buildModel();
        } catch (spoon.compiler.ModelBuildingException e) {
            e.printStackTrace();
            logger.print("Failed to build spoon model. " + e.getMessage());
        } catch (spoon.SpoonException e) {
            logger.print("Failed to build spoon model. " + e.getMessage());
        }
    }


    /**
     * Code that performs high level task delegation from the spoon model
     */
    @Override
    public void run() {
        Collection<CtPackage> packages = spoon.getModel().getAllPackages();
        for (CtPackage ctPackage : packages) {
            handlePackage(ctPackage); // TODO: maybe a task per package into the threadpool
        }
    }

    private void handlePackage(CtPackage ctPackage) {
        logger.print("Package: " + ctPackage.getQualifiedName());
        Future report = null;
        for (CtType ctType : ctPackage.getTypes()) {
            // TODO do something with Future's result
            logger.print("\tType: " + ctType.getSimpleName());
            report = threadPool.submit(new ClassScanner(threadPool, factoryManager, ctType));
        }
        if (report != null) {
            try {
                logger.print(report.get().toString());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Updatable method that return the format in which command line arguments should be given
     *
     * @return the format in which command line arguments should be given
     */
    static String getUsage() {
        return "<filename|foldername> [<userSettings.json>] [<Patterns.java>]";
    }
}
