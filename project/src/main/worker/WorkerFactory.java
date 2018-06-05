package worker;

import spoon.reflect.declaration.CtElement;

public abstract class WorkerFactory {
    private final String patternName;


    protected WorkerFactory(String patternName) {
        this.patternName = patternName;
    }

    public String getPatternName() {
        return patternName;
    }

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

}
