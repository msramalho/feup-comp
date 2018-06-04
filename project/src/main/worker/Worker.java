package worker;

import main.Configuration;
import report.WorkerReport;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.AbstractFilter;
import util.Logger;
import util.Operations;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Class from which the Workers should inherit.
 * Callable method should return a report of the worker's run.
 */
public abstract class Worker implements Callable { // running call on ExecutorService returns Future<C>
    private AbstractFilter filter; // filter to match this worker with the CtElement which triggers it
    private String patternName;
    protected CtElement rootNode;
    protected Map<String, Function<Stream<WorkerReport>, Number>> operations = new HashMap<>(Configuration.operations);

    protected Logger logger = new Logger(this); // TODO: delete for production (?)

    public Worker(CtElement rootNode, String patternName) {
        this.rootNode = rootNode;
        this.patternName = patternName;
    }

    protected CtElement getCtElement() { return rootNode; }

    /**
     * Template method for getting the filter variable, should be called in constructor
     */
    protected abstract AbstractFilter setFilter();

    /**
     * Template method for setting the filter variable, should be called in constructor
     */
    void loadFilter() {this.filter = setFilter(); }

    Class<? extends CtElement> getType() { return filter.getType(); }

    /**
     * method that uses the filter to test a given CtElement
     *
     * @param c the CtElement to test against the Filter
     * @return true if there is a match
     */
    boolean matches(CtElement c) { return filter != null && filter.matches(c); }

    public String getPatternName() { return patternName; }

    @Override
    public abstract WorkerReport call() throws Exception;

    /**
     * By default every Worker is counted, this method can be overridden to use more {@link Operations}
     *
     * @return the operations to be performed on the Pattern matching the current {@link Worker}
     */
    public Map<String, Function<Stream<WorkerReport>, Number>> getOperations() {
        return operations;
    }

}
