package worker.workers;

import report.WorkerReport;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import worker.Worker;

/**
 * Returns the number of lines of code in a class, this will both format into a generalized format for all Java code and remove comments
 */
public class W_linesOfCodeClass extends Worker {
    public W_linesOfCodeClass(CtElement rootNode, String patternName) {
        super(rootNode, patternName);
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter<>(CtClass.class);
    }

    @Override
    public WorkerReport call() throws Exception {
        return new WorkerReport(rootNode.toString().split("\r\n|\r|\n").length);
    }
}
