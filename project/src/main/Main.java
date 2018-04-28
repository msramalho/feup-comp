package main;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1 || args.length > 2) {
            System.out.println("Unable to parse command line arguments, usage: " + Dispatcher.getUsage());
            System.exit(0);
        }
        String targetFile = args[0];

        String configFile = args.length == 2 ? args[1] : null;
        Dispatcher dispatcher = initializeDispatcher(configFile);

        processTargetFile(dispatcher, targetFile);

        dispatcher.run();
    }

    private static void processTargetFile(Dispatcher dispatcher, String targetFile) {
        try {
            dispatcher.readSpoonTarget(targetFile);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
    }

    private static Dispatcher initializeDispatcher(String configFile) {
        Dispatcher dispatcher;
        if (configFile != null) {
            Configuration config = Configuration.loadConfiguration(configFile);
            dispatcher = new Dispatcher(config);
        } else {
            dispatcher = new Dispatcher();
        }

        return dispatcher;
    }
}
