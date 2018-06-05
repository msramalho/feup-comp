package worker.workers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import report.WorkerReport;
import spoon.reflect.code.CtComment;
import spoon.support.reflect.code.CtBlockImpl;
import spoon.support.reflect.code.CtCommentImpl;

import static org.junit.jupiter.api.Assertions.*;

class W_classCommentsJavadocTest {

    private static CtCommentImpl c = new CtCommentImpl();
    private static W_classCommentsJavadoc w = new W_classCommentsJavadoc(c, "");

    @BeforeEach
    void setUp() {
        w.filter = w.setFilter();
    }

    @Test
    void setFilter() {
        assertTrue(w.filter.matches(c));
        assertFalse(w.filter.matches(new CtBlockImpl()));
    }

    @Test
    void call() throws Exception {
        assertNotEquals((new WorkerReport(1)).getValue(), w.call().getValue());
        c.setCommentType(CtComment.CommentType.JAVADOC);
        assertEquals((new WorkerReport(1)).getValue(), w.call().getValue());
    }

}