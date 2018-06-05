package worker.workers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import report.WorkerReport;
import spoon.reflect.code.CtConditional;
import spoon.support.reflect.code.CtBlockImpl;
import spoon.support.reflect.code.CtConditionalImpl;
import spoon.support.reflect.code.CtForImpl;

import static org.junit.jupiter.api.Assertions.*;

class W_ternaryTest {
    private static CtConditionalImpl c = new CtConditionalImpl();
    private static W_ternary w = new W_ternary(c, "");
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
        assertEquals((new WorkerReport(1)).getValue(), w.call().getValue());
    }

}