package main;

import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.CtType;
import util.Logger;
import util.Report;
import worker.WorkerFactory;

import java.io.FileNotFoundException;
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

    public Dispatcher(String targetFolder) throws FileNotFoundException {
        this(targetFolder, null);
    }

    public Dispatcher(String targetFolder, String configFile) throws FileNotFoundException {
        if (!Files.exists(Paths.get(targetFolder)))
            throw new FileNotFoundException(targetFolder + " does not exist.");

        // save the SPOON target Folder
        spoonTarget = targetFolder;

        //if there are 2 arguments load config from JSON file, else use default configuration
        configuration = configFile != null ? Configuration.loadConfiguration(configFile) : new Configuration();

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
        final List<WorkerFactory> workerFactories = configuration.getActiveDynamicFeatures();

        Collection<CtPackage> packages = spoon.getModel().getAllPackages();
        for (CtPackage ctPackage : packages) {
            handlePackage(workerFactories, ctPackage);
        }
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
    static String getUsage() {
        return "<filename|foldername> [<userSettings.json>]";
    }
}
