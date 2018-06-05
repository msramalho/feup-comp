package worker.workers;

import report.WorkerReport;
import spoon.reflect.code.CtDo;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import worker.Worker;

/**
 * Find do while loops
 */
public class W_loopsDoWhile extends Worker {
    public W_loopsDoWhile(CtElement rootNode, String patternName) {
        super(rootNode, patternName);
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter<>(CtDo.class);
    }

    @Override
    public WorkerReport call() {
        return new WorkerReport(1);
    }
}
