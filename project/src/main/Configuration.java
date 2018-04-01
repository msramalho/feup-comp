package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Configuration {

    public class Static {
        public Boolean countFor = false;
        public Boolean countForeach = false;

        Static() {
        }
    }

    public class Output {
        public String path = "out/";
        public String format = "json";

        Output() {
        }
    }

    public class Global{
        public int numberOfThreads = 16;
        public Global() {
        }
    }

    Static fix = new Static();
    Output output = new Output();
    Global global = new Global();

    Configuration() {
    }

    /**
     * Read a configurations file into an object of Configuration
     *
     * @param filename the name of the json file containing the configurations
     * @return Configuration read from the file
     */
    public static Configuration loadConfiguration(String filename) {
        Gson gson = new Gson();
        return gson.fromJson(settingsFileContent(filename), Configuration.class);
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
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
