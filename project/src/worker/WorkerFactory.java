package worker;

import report.PatternReport;
import spoon.reflect.declaration.CtElement;

import java.util.concurrent.Future;

public abstract class WorkerFactory {
    PatternReport report;

    /**
     * Creates a new instance of a Worker, with the given ctElement as root node.
     *
     * @return A Worker instance.
     */
    public abstract Worker makeWorker(CtElement ctElement);

    /**
     * Test if a given CtElement should be handled by the Workers this factory produces
     *
     * @param ctElement the CtElement to test against the filterWorker
     * @return true if there is a match
     */
    public abstract boolean matches(CtElement ctElement);


    /**
     * Getter for the Class type of CtElement that triggers this factory's workers.
     *
     * @return The Class that triggers the creation of a Worker.
     */
    public abstract Class<?> getType();

    public void addFuture(Future future) { report.addFuture(future); }

    public PatternReport getPatternReport() { return report; }

}
