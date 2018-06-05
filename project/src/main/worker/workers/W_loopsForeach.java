package worker.workers;

import report.WorkerReport;
import spoon.reflect.code.CtForEach;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import worker.Worker;

/**
 * Find foreach loops
 */
public class W_loopsForeach extends Worker {
    public W_loopsForeach(CtElement rootNode, String patternName) {
        super(rootNode, patternName);
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter<>(CtForEach.class);
    }

    @Override
    public WorkerReport call() {
        return new WorkerReport(1);
    }
}
