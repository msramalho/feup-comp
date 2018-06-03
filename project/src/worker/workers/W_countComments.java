package worker.workers;

import report.WorkerReport;
import spoon.reflect.code.CtComment;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import worker.Worker;

public class W_countComments extends Worker {
    public W_countComments(CtElement rootNode, String patternName) {
        super(rootNode, patternName);
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter(CtComment.class);
    }

    @Override
    public WorkerReport call() throws Exception {
        return new WorkerReport(1); // return 1 for each comment found
    }
}
