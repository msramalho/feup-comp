package worker.workers;

import report.WorkerReport;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtField;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import worker.Worker;

/**
 * Count the number of fields in a given class
 */
public class W_countFieldsPerClass extends Worker {
    public W_countFieldsPerClass(CtElement rootNode, String patternName) {
        super(rootNode, patternName);
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter<>(CtField.class);
    }

    @Override
    public WorkerReport call() throws Exception {
        return new WorkerReport(1);
    }
}
