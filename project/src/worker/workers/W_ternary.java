package worker.workers;

import report.WorkerReport;
import spoon.reflect.code.CtConditional;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import worker.Worker;

public class W_ternary extends Worker {
    public W_ternary(CtElement rootNode, String patternName) {
        super(rootNode, patternName);
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter<>(CtConditional.class);
    }

    @Override
    public WorkerReport call() throws Exception {
        return new WorkerReport(1);
    }
}
