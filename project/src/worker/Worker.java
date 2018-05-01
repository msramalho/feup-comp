package worker;

import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.Filter;
import spoon.reflect.visitor.filter.AbstractFilter;
import util.Logger;

import java.util.concurrent.Callable;

/**
 * Class from which the Workers should inherit.
 * Callable method should return a report of the worker's run.
 */
public abstract class Worker implements Callable { // running call on ExecutorService returns Future<C>
    private AbstractFilter filter; // filter to match this worker with the CtElement which triggers it
    Logger logger;
    CtElement rootNode;

    public Worker(CtElement rootNode) {
        this.rootNode = rootNode;
        this.filter = setFilter();
        this.logger = new Logger(this); // TO DELETE, or guarantee access to Logger file thread safe
    }

    protected CtElement getCtElement() {
        return rootNode;
    }

    /**
     * Template method for setting the filter variable, should be called in constructor
     */
    protected abstract AbstractFilter setFilter();

    Class<? extends CtElement> getType() {
        return filter.getType();
    } // TODO check template parameter bound

    /**
     * method that uses the filter to test a given CtElement
     *
     * @param c the CtElement to test against the Filter
     * @return true if there is a match
     */
    boolean matches(CtElement c) {
        return filter.matches(c);
    }

}
