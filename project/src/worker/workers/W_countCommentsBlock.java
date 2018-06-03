package worker.workers;

import report.WorkerReport;
import spoon.reflect.code.CtComment;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import worker.Worker;

/**
 * Counts the number of block comments
 */
public class W_countCommentsBlock extends Worker {
    public W_countCommentsBlock(CtElement rootNode, String patternName) {
        super(rootNode, patternName);
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter(CtComment.class);
    }

    @Override
    public WorkerReport call() throws Exception {
        // return 1 for each block comment found
        return new WorkerReport((((CtComment) rootNode).getCommentType() == CtComment.CommentType.BLOCK) ? 1 : 0);
    }
}
