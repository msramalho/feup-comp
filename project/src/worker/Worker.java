package worker;

import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.Filter;
import util.Logger;

import java.util.concurrent.Callable;

/**
 * Class from which the Workers should inherit
 */
public abstract class Worker<C> implements Callable<C> { // running call on ExecutorService returns Future<C>
    private Filter filter; // the filter that, upon true, should trigger this worker
    Logger logger;
    CtElement source;

    Worker(CtElement source) {
        this.source = source;
        this.logger = new Logger(this);
        this.filter = setFilter();
    }

     CtElement getCtElement() { return source; }

    /**
     * Template method for setting the filter variable, should be called in constructor
     */
    protected abstract Filter setFilter();

    /**
     * method that uses the filter to test a given CtElement
     *
     * @param c the CtElement to test against the Filter
     * @return true if there is a match
     */
    boolean matches(CtElement c) {
        return filter.matches(c);
    }
    // TODO check unchecked method call, probably object should be bounded

}
