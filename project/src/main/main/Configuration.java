package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import report.WorkerReport;
import util.Logger;
import util.Operations;
import worker.StaticWorkerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class Configuration {
    public transient static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public transient Logger logger = new Logger(this);
    public transient static Map<String, Function<Stream<WorkerReport>, Number>> operations = new HashMap<>();

    public class Static {
        public String patternsFile = "./patterns/Patterns.java";

        Static() { }
    }

    public class Dynamic {
        public boolean innerLoops = false;
        public boolean possibleTernary = false;
        public boolean ternary = false;
        public boolean classInheritance = false; //TODO: is this alive? else remove
        public boolean cyclomaticComplexity = false;
        public boolean loopsFor = false;
        public boolean loopsForeach = false;
        public boolean loopsWhile = false;
        public boolean classMethods = false;
        public boolean classMethodsPublic = false;
        public boolean classMethodsProtected = false;
        public boolean classMethodsPrivate = false;
        public boolean classMethodsStatic = false;
        public boolean classMethodsAbstract = false;
        public boolean linesOfCodeClass = false;
        public boolean linesOfCodeMethod = false;
        public boolean classComments = false;
        public boolean classCommentsInline = false;
        public boolean classCommentsBlock = false;
        public boolean classCommentsJavadoc = false;
        public boolean classFields = false;
        public boolean classFieldsPublic = false;
        public boolean classFieldsProtected = false;
        public boolean classFieldsPrivate = false;
        public boolean classFieldsStatic = false;
        public boolean superClasses = false;
        public boolean superClassesJava = false;
        public boolean weightedMethodCountLoC = false; // weighted method count using lines of code
        public boolean weightedMethodCountCC = false;  // weighted method count using cyclomatic complexity
        public boolean weightedMethodCountNoM = false; // weighted method count using number of methods

        Dynamic() { }
    }


    public class Output {
        public String path = "out/";
        public String format = "json";

        Output() { }
    }

    public class Global {
        public int numberOfThreads = 16;
        public boolean parseComments = false; // if true lines of code will include comments, if false no comment pattern will work
        public String[] operations = {"sum"}; //TODO: check why this is null unless in settings.json

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
    static Configuration loadConfiguration(String filename) {
        return gson.fromJson(settingsFileContent(filename), Configuration.class);
    }


    /**
     * Read the properties of this.dynamic and, for each true property, add a new StaticWorkerFactory(name, worker, filter) to the result
     *
     * @return a list of features to analyze, along with the respective Worker to create (which has the spoon filter)
     */
    List<StaticWorkerFactory> getActiveWorkerFactories() {
        // parse the user-defined list of String for the operations into the Operations.something equivalent
        operations = Operations.parseOperations(global.operations);

        ArrayList<StaticWorkerFactory> workerFactories = new ArrayList<>();
        for (Field f : Dynamic.class.getDeclaredFields()) {
            try {
                Object dynamicField = f.get(dynamic);
                if (dynamicField != null && (dynamicField instanceof Boolean) && (Boolean) dynamicField) // the user wants this feature
                    workerFactories.add(new StaticWorkerFactory(f.getName()));

            } catch (IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InstantiationException | InvocationTargetException e) {
                System.err.println(String.format("Unable to find the worker matching %s. Should be: %s", f.getName(), StaticWorkerFactory.getWorkerName(f.getName())));
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
