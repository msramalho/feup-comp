package main;

import pattern_matcher.PatternDefinitions;
import spoon.reflect.declaration.CtElement;
import worker.DynamicWorkerFactory;
import worker.StaticWorkerFactory;
import worker.WorkerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main implements Runnable {
    private Dispatcher dispatcher;
    private Configuration configuration;
    private FactoryManager factoryManager;
    private HashMap<String, HashMap<String, Future<Node>>> packageNodes = null;

    /**
     * Main function should parse cmd args
     *
     * @param args to parse
     */
    public static void main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            System.out.println("Unable to parse command line arguments, usage: " + Dispatcher.getUsage());
            System.exit(0);
        }

        String targetFile = args[0];
        String configFile = args.length >= 2 ? args[1] : null;

        Main obj = new Main(targetFile, configFile);
        obj.run();
    }

    Main(String targetFile, String configFile) {
        configuration = initializeConfiguration(configFile);
        dispatcher = initializeDispatcher(targetFile);

        factoryManager = initializeFactoryManager(configuration.fix.patternsFile);
        dispatcher.setFactoryManager(factoryManager);
    }

    private Configuration initializeConfiguration(String configFile) {
        System.out.println(configFile);
        Configuration configuration;
        if (configFile == null) {
            configuration = new Configuration();
        } else {
            configuration = Configuration.loadConfiguration(configFile);
        }

        return configuration;
    }

    private Dispatcher initializeDispatcher(String targetFile) {
        dispatcher = new Dispatcher(configuration);
        processTargetFile(targetFile);

        return dispatcher;
    }

    private void processTargetFile(String targetFile) {
        try {
            dispatcher.readSpoonTarget(targetFile);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
    }


    private FactoryManager initializeFactoryManager(String patternsFile) {
        FactoryManager manager = new FactoryManager();
        addStaticWorkerFactories(manager, configuration);
        addDynamicWorkerFactories(manager, patternsFile);

        return manager;
    }

    private void addStaticWorkerFactories(FactoryManager manager, Configuration configuration) {
        if (configuration == null)
            return;

        List<StaticWorkerFactory> factories = configuration.getActiveWorkerFactories();
        for (WorkerFactory factory : factories) {
            manager.addWorkerFactory(factory);
        }
    }

    private void addDynamicWorkerFactories(FactoryManager manager, String patternsFile) {
        PatternDefinitions patternDefinitions;
        try {
            patternDefinitions = new PatternDefinitions(patternsFile);
        } catch (FileNotFoundException e) {
            System.out.println("Patterns file not found: " + e.getMessage());
            return;
        }

        for (Map.Entry<Class<?>, List<CtElement>> entry : patternDefinitions.getPatterns().entrySet()) {
            for (CtElement block : entry.getValue()) {
                manager.addWorkerFactory(new DynamicWorkerFactory(entry.getKey(), block));
            }
        }
    }

    /**
     * call dispatcher and get the results, then create a report
     */
    @Override
    public void run() {
        try {
            packageNodes = (HashMap<String, HashMap<String, Future<Node>>>) dispatcher.call();
            writeReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeReport() throws ExecutionException {
        for (Map.Entry<String, HashMap<String, Future<Node>>> p : packageNodes.entrySet()) {//patterns
            // create a folder for each package
            String folderName = configuration.output.path + "/" + p.getKey() + "/";
            new File(folderName).mkdirs(); // create dirs

            for (Map.Entry<String, Future<Node>> t : p.getValue().entrySet()) {//types inside patterns
                //create a report for each Type inside that folder
                String filename = folderName + t.getKey() + "." + configuration.output.format;
                try {
                    byte data[] = (t.getValue().get()).getReport().toString().getBytes();
                    Path file = Paths.get(filename);
                    Files.write(file, data);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
