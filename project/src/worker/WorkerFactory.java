package worker;

import main.Configuration;
import spoon.reflect.declaration.CtElement;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;

public class WorkerFactory {
    String name;
    Configuration configuration;
    Class<?> workerClass;
    private Worker filterWorker;

    /**
     * Receives the name of the feature and the configuration and loads the proper worker, through its super constructor that receives only a Configuration object
     *
     * @param name          of the feature
     * @param configuration pointer to the program configuration
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public WorkerFactory(String name, Configuration configuration) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        this.name = name;
        this.configuration = configuration;
        workerClass = Class.forName(getWorkerName(name));
        //test the creation of a new worker, and also keep it for the matches function
        filterWorker = (Worker) workerClass.getDeclaredConstructor(Configuration.class, CtElement.class).newInstance(configuration, null);
    }

    /**
     * Test if a given CtElement should be handled by the Workers this factory produces
     * @param c the CtElement to test against the filterWorker
     * @return true if there is a match
     */
    public boolean matches(CtElement c) { return filterWorker.matches(c); }

    public Worker getWorker(CtElement c) {
        try {
            return (Worker) workerClass.getDeclaredConstructor(Configuration.class, CtElement.class).newInstance(configuration, c);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;//TODO: what kind of runnable/callable should be returned so that the submit (that call this) does not crash
    }


    /**
     * converts the name of a feature into the expected name of the corresponding worker
     *
     * @param name name of the feature
     * @return the package path into the Worker
     */
    public static String getWorkerName(String name) {
        return "worker.W_" + name;
    }
}
