package worker.workers;

import report.WorkerReport;
import spoon.reflect.code.CtFor;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import worker.Worker;

/**
 * Find foreach loops
 */
public class W_loopsFor extends Worker {
    public W_loopsFor(CtElement rootNode, String patternName) {
        super(rootNode, patternName);
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter<>(CtFor.class);
    }

    @Override
    public WorkerReport call() throws Exception {
        return new WorkerReport(1);
    }
}
