package worker;

import spoon.reflect.declaration.CtElement;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class StaticWorkerFactory extends WorkerFactory {
    private static String WORKERS_LOCATION = "worker.W_";
    private Class<?> workerClass;
    private Worker filterWorker;
    private Constructor<?> constructor;

    /**
     * Receives the name of the feature and the configuration and loads the proper worker, through its super constructor that receives only a Configuration object
     *
     * @param patternName of the feature
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public StaticWorkerFactory(String patternName) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        super(patternName);
        workerClass = Class.forName(getWorkerName(patternName));
        constructor = workerClass.getDeclaredConstructor(CtElement.class, String.class);
        //test the creation of a new worker, and also keep it for the matches function
        filterWorker = (Worker) constructor.newInstance((Object) null, null);
        filterWorker.loadFilter();
    }

    @Override
    public boolean matches(CtElement c) { return filterWorker.matches(c); }

    @Override
    public Class<?> getType() { return filterWorker.getType(); }

    @Override
    public Worker makeWorker(CtElement c) {
        try {
            Worker newWorker = (Worker) constructor.newInstance(c, getPatternName());
            newWorker.loadFilter();
            return newWorker;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
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
    public static String getWorkerName(String name) { return WORKERS_LOCATION + name; }
}
