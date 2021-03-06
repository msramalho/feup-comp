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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Dispatcher implements Callable {
    private Configuration configuration;
    private ExecutorService threadPool;
    private SpoonAPI spoon;

    private FactoryManager factoryManager;


    Dispatcher(Configuration config) {
        if (config == null)
            throw new NullPointerException("Configuration file is NULL.");
        this.configuration = config;

        threadPool = Executors.newFixedThreadPool(configuration.global.numberOfThreads);

        Logger.print(this, configuration.toString());
    }

    void setFactoryManager(FactoryManager factoryManager) {
        this.factoryManager = factoryManager;
    }

    /**
     * try to build a SPOON model into spoon, will display errors upon failure
     */
    void readSpoonTarget(String spoonTarget) throws FileNotFoundException {
        if (!Files.exists(Paths.get(spoonTarget)))
            throw new FileNotFoundException(spoonTarget + " does not exist.");

        spoon = new Launcher();
        try {
            spoon.addInputResource(spoonTarget);
            spoon.getEnvironment().setCommentEnabled(configuration.global.parseComments);
            spoon.buildModel();
        } catch (spoon.compiler.ModelBuildingException e) {
            e.printStackTrace();
            Logger.print(this, "Failed to build spoon model. " + e.getMessage());
        } catch (spoon.SpoonException e) {
            Logger.print(this, "Failed to build spoon model. " + e.getMessage());
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
        Map<String, Map<String, Future<Node>>> packageNodes = new HashMap<>();
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
    private Map<String, Future<Node>> handlePackage(CtPackage ctPackage) {
        Logger.print(this, "Package: " + ctPackage.getQualifiedName());

        Map<String, Future<Node>> typeNodes = new HashMap<>();
        for (CtType ctType : ctPackage.getTypes()) {
            Logger.print(this, "\tType: " + ctType.getSimpleName() + " - " + ctType.getActualClass().getName());
            typeNodes.put(ctType.getSimpleName(), threadPool.submit(new ClassScanner(threadPool, factoryManager, ctType)));
        }
        return typeNodes;
    }


    /**
     * Updatable method that return the format in which command line arguments should be given
     *
     * @return the format in which command line arguments should be given
     */
    static String getUsage() { return "<filename|foldername> [<userSettings.json>] [DEBUG]"; }

}
