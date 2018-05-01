package main;

import pattern_matcher.PatternDefinitions;
import spoon.reflect.code.CtBlock;
import spoon.reflect.declaration.CtElement;
import worker.DynamicWorkerFactory;
import worker.StaticWorkerFactory;
import worker.WorkerFactory;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main implements Runnable {
    private Dispatcher dispatcher;
    private Configuration configuration;
    private FactoryManager factoryManager;

    public static void main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            System.out.println("Unable to parse command line arguments, usage: " + Dispatcher.getUsage());
            System.exit(0);
        }

        String targetFile = args[0];
        String configFile = args.length >= 2 ? args[1] : null;
        String patternsFile = args.length >= 3 ? args[2] : "patterns/Patterns.java";

        Main obj = new Main(targetFile, configFile, patternsFile);
        obj.run();
    }

    Main(String targetFile, String configFile, String patternsFile) {
        configuration = initializeConfiguration(configFile);
        dispatcher = initializeDispatcher(targetFile);

        factoryManager = initializeFactoryManager(patternsFile);
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

        List<StaticWorkerFactory> factories = configuration.getActiveDynamicFeatures();
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

        for (Map.Entry<Class<?>, ArrayList<CtElement>> entry : patternDefinitions.getPatterns().entrySet()) {
            for (CtElement block : entry.getValue()) {
                manager.addWorkerFactory(new DynamicWorkerFactory(entry.getKey(), (CtBlock) block));
            }
        }
    }

    @Override
    public void run() {
        dispatcher.run();
    }
}
