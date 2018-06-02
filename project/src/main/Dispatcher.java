package main;

import report.WorkerReport;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.CtType;
import util.Logger;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.*;

public class Dispatcher implements Callable {
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
     *
     * @return a {@link HashMap} of packageName->{Hashmap of TypeName->{@link Node}}
     * @throws Exception
     */
    @Override
    public Object call() throws Exception {
        HashMap<String, HashMap<String, Future<Node>>> packageNodes = new HashMap<>();
        Collection<CtPackage> packages = spoon.getModel().getAllPackages();
        for (CtPackage ctPackage : packages)
            packageNodes.put(ctPackage.getQualifiedName(), handlePackage(ctPackage));
        return packageNodes;
    }

    /**
     * Iterates over the Types inside a given package and triggers works for it
     *
     * @param ctPackage the current package
     * @return a {@link HashMap} of TypeName->{@link Node}
     */
    private HashMap<String, Future<Node>> handlePackage(CtPackage ctPackage) {
        logger.print("Package: " + ctPackage.getQualifiedName());
        // TODO improve what is to be done with the report
        HashMap<String, Future<Node>> typeNodes = new HashMap<>();
        for (CtType ctType : ctPackage.getTypes()) {
            logger.print("\tType: " + ctType.getSimpleName() + " - " + ctType.getActualClass().getName());
            typeNodes.put(ctType.getSimpleName(), threadPool.submit(new ClassScanner(threadPool, factoryManager, ctType)));
        }
        return typeNodes;
    }


    /**
     * Updatable method that return the format in which command line arguments should be given
     *
     * @return the format in which command line arguments should be given
     */
    static String getUsage() { return "<filename|foldername> [<userSettings.json>]"; }

}
