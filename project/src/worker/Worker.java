package worker;

import main.Configuration;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.Filter;
import util.Logger;

import java.util.concurrent.Callable;

/**
 * Class from which the Workers should inherit
 *
 * TODO
 * -> Todos os Workers devem ter uma interface comum que reporta resultados dinâmicos e estáticos do nó respetivo,
 * returna num Future de classe resultados.
 * -> Workers da classe devem poder lançar novos workers, e só retornar quando os workers filho retornam,
 * reportanto a soma dos resutlados deles.d
 */
public abstract class Worker implements Callable {
    // TODO why not extend CtVisitor? or CtAbstractVisitor, or CtScanner
    // TODO  additionally, all interfaces compatible with CtVisitor would be pretty useful
    Configuration configuration; // TODO reassess why this is needed here
    Filter filter; // the filter that, upon true, should trigger this worker
    Logger logger;
    CtElement element;

    public Worker(Configuration configuration, CtElement element) {
        this.configuration = configuration;
        this.element = element;
        logger = new Logger(this);
        this.setFilter();
    }

    /**
     * Template method for setting the filter variable, should be called in constructor
     */
    protected abstract void setFilter();

    /**
     * method that uses the filter to test a given CtElement
     *
     * @param c the CtElement to test against the Filter
     * @return true if there is a match
     */
    public boolean matches(CtElement c) { return filter.matches(c); }
    // TODO check unchecked method call, probably object should be bounded

}
