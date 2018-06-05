package worker.workers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import report.WorkerReport;
import spoon.support.reflect.code.CtBlockImpl;
import spoon.support.reflect.code.CtDoImpl;

import static org.junit.jupiter.api.Assertions.*;

class W_loopsDoWhileTest {
    private static CtDoImpl c = new CtDoImpl();
    private static W_loopsDoWhile w = new W_loopsDoWhile(c, "");

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
    void call() {
        assertEquals((new WorkerReport(1)).getValue(), w.call().getValue());
    }

}