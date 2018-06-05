package worker.workers;

import report.WorkerReport;
import spoon.reflect.code.CtComment;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import worker.Worker;

/**
 * Counts the number of comments: Inline, Block, File and JavaDoc
 */
public class W_classComments extends Worker {
    public W_classComments(CtElement rootNode, String patternName) {
        super(rootNode, patternName);
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter<>(CtComment.class);
    }

    @Override
    public WorkerReport call() {
        return new WorkerReport(1); // return 1 for each comment found
    }
}
