package worker.workers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import report.WorkerReport;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.support.reflect.code.CtBlockImpl;
import util.CtIterator;

import static org.junit.jupiter.api.Assertions.*;

class W_innerLoopsTest {
    private static CtClass c = Launcher.parseClass("class A { " +
            "protected double pp2(){ for(int i=0;i<10;i++){if(true) return true?1.0:0.0;}}" +
            "public int cenas() { for (int i = 0; i < 10; i++) { if (true) return 0; while (true) { Integer[] ss = {1, 2, 3}; for (Integer s : ss) { for (int j = 0; j < s; j++) { return j * j; } } } } for (int i = 0; i < 10; i++) { return i * i * i; } return 10; }}");

    private static W_innerLoops w = new W_innerLoops(c, "");
    private static CtIterator i;

    @BeforeEach
    void setUp() {
        w.filter = w.setFilter();
        i = new CtIterator(c);
    }

    @Test
    void setFilter() {
        while (i.hasNext()) {
            CtElement ct = (CtElement) i.next();
            if (ct.getClass().getName().contains("Method")) assertTrue(w.filter.matches(ct));
            else assertFalse(w.filter.matches(ct));
        }
    }

    @Test
    void call() {
        int[] results = {1, 4};
        int j = 0;
        while (i.hasNext()) {
            CtElement ct = (CtElement) i.next();
            if (ct.getClass().getName().contains("Method")) {
                w = new W_innerLoops(ct, "");
                int v = w.call().getValue();
                assertEquals(results[j++], v);
            }
        }
    }

}