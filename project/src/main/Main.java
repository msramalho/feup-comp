package main;

public class Main {
    public static void main(String[] args) {
        Dispatcher dispatcher;

        try {
            dispatcher = new Dispatcher(args);
        } catch (NonExistentFileException e) {
            System.err.println(e.getMessage());
            return;
        } catch (Exception e) {
            System.out.println("Unable to parse command line arguments, usage: " + Dispatcher.getUsage());
            e.printStackTrace();
            return;
        }

        // TODO: decide if this should be called inside the constructor, maybe it can receive a new spoonTarget as arg, to readSpoon multiple times for the same dispatcher
        dispatcher.readSpoon();

        dispatcher.run();

    }
}
