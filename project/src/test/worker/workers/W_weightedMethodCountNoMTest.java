package worker.workers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import report.WorkerReport;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.support.reflect.code.CtBlockImpl;
import util.CtIterator;

import static org.junit.jupiter.api.Assertions.*;

class W_weightedMethodCountNoMTest {
    private static CtClass c = Launcher.parseClass("class A { " +
            "public int ii(){ return 10;} " +
            "void m() { if(true) return;} " +
            "private String s(){ if(true)return null;else return \"asd\";} " +
            "protected double pp1(){ if(true) return true?1.0:0.0;} " +
            "protected double pp2(){ for(int i=0;i<10;i++){if(true) return true?1.0:0.0;}} " +
            "public int fff(){retunr 0;}");

    private static W_weightedMethodCountNoM w = new W_weightedMethodCountNoM(c, "");
    private static CtIterator i;

    @BeforeEach
    void setUp() {
        w.filter = w.setFilter();
        i = new CtIterator(c);
    }

    @Test
    void setFilter() {
        assertTrue(w.filter.matches(c));
        assertFalse(w.filter.matches(new CtBlockImpl()));
    }

    @Test
    void call() throws Exception {
        assertEquals((new WorkerReport(6)).getValue(), w.call().getValue());
    }

}