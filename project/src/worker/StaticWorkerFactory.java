package worker;

import spoon.reflect.declaration.CtElement;

import java.lang.reflect.InvocationTargetException;

public class StaticWorkerFactory implements WorkerFactory {
    String name;
    Class<?> workerClass;
    private Worker filterWorker;

    /**
     * Receives the name of the feature and the configuration and loads the proper worker, through its super constructor that receives only a Configuration object
     *
     * @param name of the feature
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public StaticWorkerFactory(String name) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        this.name = name;
        workerClass = Class.forName(getWorkerName(name));
        //test the creation of a new worker, and also keep it for the matches function
        filterWorker = (Worker) workerClass.getDeclaredConstructor(CtElement.class).newInstance((Object) null);
    }

    @Override
    public boolean matches(CtElement c) {
        return filterWorker.matches(c);
    }

    @Override
    public Class<? extends CtElement> getType() {
        return filterWorker.getType();
    }

    @Override
    public Worker makeWorker(CtElement c) {
        try {
            return (Worker) workerClass.getDeclaredConstructor(CtElement.class).newInstance(c);
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
