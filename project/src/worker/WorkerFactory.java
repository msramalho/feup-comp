package worker;

import report.PatternReport;
import spoon.reflect.declaration.CtElement;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Future;

public class WorkerFactory {
    String name;
    Class<?> workerClass;
    private Worker filterWorker;
    PatternReport patternReport; /** A Pattern report with all the worker reports from its spawned children */

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
    public WorkerFactory(String name) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        this.name = name;
         patternReport = new PatternReport(name);
        workerClass = Class.forName(getWorkerName(name));
        //test the creation of a new worker, and also keep it for the matches function
        filterWorker = (Worker) workerClass.getDeclaredConstructor(CtElement.class).newInstance((Object) null);
    }

    /**
     * Test if a given CtElement should be handled by the Workers this factory produces
     *
     * @param c the CtElement to test against the filterWorker
     * @return true if there is a match
     */
    public boolean matches(CtElement c) {
        return filterWorker.matches(c);
    }

    public Worker getWorker(CtElement c) {
        // TODO: useful for problem c.getFactory().Package().getRootPackage().getPackage("teste").getTypes(); // direct children, tirar
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

    public void addFuture(Future future) {
        patternReport.addFuture(future);
    }

    public PatternReport getPatternReport() {
        return patternReport;
    }
}
