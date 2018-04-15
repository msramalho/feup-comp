package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import worker.WorkerFactory;
import util.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Configuration {
    public transient static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public transient Logger logger = new Logger(this);

    public class Static {
        public Boolean countFor = false;
        public Boolean countForeach = false;

        Static() { }
    }

    public class Dynamic {
        public boolean innerLoops = false;
        public boolean classInheritance = false;

        public Dynamic() { }
    }


    public class Output {
        public String path = "out/";
        public String format = "json";

        Output() { }
    }

    public class Global {
        public int numberOfThreads = 16;

        public Global() { }
    }


    @SerializedName("static")
    Static fix = new Static();
    Output output = new Output();
    Global global = new Global();
    Dynamic dynamic = new Dynamic();

    Configuration() { }

    /**
     * Read a configurations file into an object of Configuration
     *
     * @param filename the name of the json file containing the configurations
     * @return Configuration read from the file
     */
    public static Configuration loadConfiguration(String filename) {
        return gson.fromJson(settingsFileContent(filename), Configuration.class);
    }


    /**
     * Read the properties of this.dynamic and, for each true property, add a new WorkerFactory(name, worker, filter) to the result
     *
     * @return a list of features to analyze, along with the respective Worker to create (which has the spoon filter)
     */
    public ArrayList<WorkerFactory> getActiveDynamicFeatures() {
        ArrayList<WorkerFactory> workerFactories = new ArrayList<>();
        for (Field f : Dynamic.class.getDeclaredFields()) {
            try {
                Object dynamicField = f.get(dynamic);
                if (dynamicField != null && (dynamicField instanceof Boolean) && ((Boolean) dynamicField).booleanValue()) // the user wants this feature
                    workerFactories.add(new WorkerFactory(f.getName(), this));

            } catch (IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InstantiationException | InvocationTargetException e) {
                logger.print(String.format("Unable to find the worker matching %s. Should be: %s", f.getName(), WorkerFactory.getWorkerName(f.getName())));
                e.printStackTrace();
            }
        }
        return workerFactories;
    }


    /**
     * Gets the content of the user settings file
     *
     * @param filename the name of the file to read
     * @return string containing the file content
     */
    private static String settingsFileContent(String filename) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(filename));
            return new String(encoded, Charset.defaultCharset());
        } catch (java.io.IOException e) {
            System.err.println("Failed to read configuration's file.");
            return null;
        }
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
