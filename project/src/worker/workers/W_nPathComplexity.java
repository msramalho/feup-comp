package worker.workers;

import report.WorkerReport;
import spoon.reflect.code.*;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import util.CtIterator;
import worker.Worker;

/**
 * Calculates the NPATH complexity of a given method
 */
public class W_nPathComplexity extends Worker {
    public W_nPathComplexity(CtElement rootNode, String patternName) {
        super(rootNode, patternName);
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter<>(CtMethod.class);
    }

    @Override
    public WorkerReport call() throws Exception {
        Integer complexity = 0;

        //TODO: waiting for control flow graph
        return new WorkerReport(complexity);
    }
}
