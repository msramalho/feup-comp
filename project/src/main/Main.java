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

        Dispatcher dispatcher;
        try {
            dispatcher = new Dispatcher(targetFile, configFile);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // TODO  decide if this should be called inside the constructor
        // TODO maybe it can receive a new spoonTarget as arg, to readSpoon multiple times for the same dispatcher
        dispatcher.readSpoon();

        dispatcher.run();
    }
}
