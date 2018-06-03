package worker.workers;

import report.WorkerReport;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.code.CtForEachImpl;
import spoon.support.reflect.code.CtLocalVariableImpl;
import worker.Worker;

public class W_innerLoops extends Worker {

    public W_innerLoops(CtElement rootNode, String patternName) {
        super(rootNode, patternName);
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter<>(CtLocalVariableImpl.class);
    }

    @Override
    public WorkerReport call() {
        return new WorkerReport(1);
    }
}
