package worker.workers;

import report.WorkerReport;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtSwitch;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import worker.Worker;

/**
 * Find switch case statements
 */
public class W_conditionalSwitch extends Worker {
    public W_conditionalSwitch(CtElement rootNode, String patternName) {
        super(rootNode, patternName);
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter<>(CtSwitch.class);
    }

    @Override
    public WorkerReport call() {
        return new WorkerReport(1);
    }
}
