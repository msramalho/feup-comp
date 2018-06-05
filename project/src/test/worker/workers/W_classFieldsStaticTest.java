package worker.workers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import report.WorkerReport;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.support.reflect.code.CtBlockImpl;

import static org.junit.jupiter.api.Assertions.*;

class W_classFieldsStaticTest {

    private static CtClass c = Launcher.parseClass("class A { private static int i, i2; public String s; protected double d; char c; void m() { System.out.println(\"yeah\");} }");

    private static W_classFieldsStatic w = new W_classFieldsStatic(c, "");

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
        assertEquals((new WorkerReport(2)).getValue(), w.call().getValue());
    }

}