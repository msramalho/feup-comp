package main;

import pattern_matcher.PatternDefinitions;
import report.Report;
import spoon.reflect.declaration.CtElement;
import util.Logger;
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

import static java.lang.System.exit;

public class Main implements Runnable {
    private Dispatcher dispatcher;
    private Configuration configuration;
    private FactoryManager factoryManager;
    private Map<String, Map<String, Future<Node>>> packageNodes = null;

    /**
     * Main function should parse cmd args
     *
     * @param args to parse
     */
    public static void main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            System.out.println("Unable to parse command line arguments, usage: " + Dispatcher.getUsage());
            exit(0);
        }

        String targetFile = args[0];
        String configFile = args.length >= 2 ? args[1] : null; // config file

        if (args.length == 3 && args[2].toUpperCase().equals("DEBUG")) {
            Logger.setSilence(false);
        } else if (args.length == 2 && args[1].toUpperCase().equals("DEBUG")) {
            Logger.setSilence(false);
            configFile = null;
        } else {
            Logger.setSilence(true);
        }

        Main obj = new Main(targetFile, configFile);
        obj.run();
    }

    private Main(String targetFile, String configFile) {
        configuration = initializeConfiguration(configFile);
        dispatcher = initializeDispatcher(targetFile);

        factoryManager = initializeFactoryManager(configuration.dynamic.patternsFile);
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
            exit(0);
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
            System.err.println("Patterns file not found: " + e.getMessage());
            return;
        }

        for (Map.Entry<Class<?>, List<CtElement>> entry : patternDefinitions.getPatterns().entrySet()) {
            for (CtElement block : entry.getValue()) {
                manager.addWorkerFactory(new DynamicWorkerFactory(entry.getKey(), block));
            }
        }
    }

    /**
     * Call dispatcher and get the execution results, then export the report.
     */
    @Override
    public void run() {
        try {
            packageNodes = (Map<String, Map<String, Future<Node>>>) dispatcher.call();
            writeReport();
            exit(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeReport() throws ExecutionException {
        Report.setPrettyPrint(configuration.global.prettyPrint);
        Report global = new Report();
        for (Map.Entry<String, Map<String, Future<Node>>> p : packageNodes.entrySet()) { //patterns
            // create a folder for each package
            String folderName = configuration.global.outputPath + "/" + p.getKey() + "/";
            new File(folderName).mkdirs(); // create dirs

            for (Map.Entry<String, Future<Node>> t : p.getValue().entrySet()) {//types inside patterns
                //create a report for each Type inside that folder
                String filename = folderName + t.getKey() + ".json";
                try {
                    Report local = (t.getValue().get()).getReport();
                    global = global.merge(local); //build global report incrementally

                    writeFile(filename, local.toString()); // write individual Type reports
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        writeFile(configuration.global.outputPath + "/report.json", global.toString()); // write individual Type reports
        System.out.println("Report exported to " + configuration.global.outputPath + "/report.json");
    }

    private void writeFile(String filename, String content) {
        byte data[] = content.getBytes();
        Path file = Paths.get(filename);
        try {
            Files.write(file, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
