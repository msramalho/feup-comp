package worker.workers;

import report.WorkerReport;
import spoon.reflect.code.CtIf;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import worker.Worker;

/**
 * Find if statements
 */
public class W_conditionalIf extends Worker {
    public W_conditionalIf(CtElement rootNode, String patternName) {
        super(rootNode, patternName);
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter<>(CtIf.class);
    }

    @Override
    public WorkerReport call() {
        return new WorkerReport(1);
    }
}
