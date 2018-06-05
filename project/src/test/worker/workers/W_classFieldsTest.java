package worker.workers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import report.WorkerReport;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.support.reflect.code.CtBlockImpl;
import spoon.support.reflect.code.CtCommentImpl;

import static org.junit.jupiter.api.Assertions.*;

class W_classFieldsTest {
    private static CtClass c = Launcher.parseClass("class A { private int i; public String s; protected double d; char c; void m() { System.out.println(\"yeah\");} }");

    private static W_classFields w = new W_classFields(c, "");

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
        assertEquals((new WorkerReport(4)).getValue(), w.call().getValue());
    }

}