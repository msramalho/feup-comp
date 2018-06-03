package worker.workers;

import report.WorkerReport;
import spoon.reflect.code.CtComment;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import worker.Worker;

/**
 * Counts the number of JavaDoc comments
 */
public class W_countCommentsJavadoc extends Worker {
    public W_countCommentsJavadoc(CtElement rootNode, String patternName) {
        super(rootNode, patternName);
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter<>(CtComment.class);
    }

    @Override
    public WorkerReport call() throws Exception {
        // return 1 for each JavaDoc comment found
        return new WorkerReport((((CtComment) rootNode).getCommentType() == CtComment.CommentType.JAVADOC) ? 1 : 0);
    }
}
