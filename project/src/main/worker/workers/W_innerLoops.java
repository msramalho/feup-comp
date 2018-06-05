package worker.workers;

import report.WorkerReport;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.code.CtLocalVariableImpl;
import worker.Worker;

/**
 * TODO: Counts the depth of loops inside a Method + test
 */
public class W_innerLoops extends Worker {

    public W_innerLoops(CtElement rootNode, String patternName) {
        super(rootNode, patternName);
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter<>(CtMethod.class);
    }

    @Override
    public WorkerReport call() {
        //TODO: using visitor enter and exit, CtIterator is unable to tell backtracks
        return new WorkerReport(1);
    }
}
