package worker.workers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import report.WorkerReport;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.support.reflect.code.CtBlockImpl;
import spoon.support.reflect.declaration.CtMethodImpl;
import util.CtIterator;

import static org.junit.jupiter.api.Assertions.*;

class W_cyclomaticComplexityTest {
    private static CtClass c = Launcher.parseClass("class A { " +
            "public int ii(){ return 10;} " +
            "void m() { if(true) return;} " +
            "private String s(){ if(true)return null;else return \"asd\";} " +
            "protected double pp1(){ if(true) return true?1.0:0.0;} " +
            "protected double pp2(){ for(int i=0;i<10;i++){if(true) return true?1.0:0.0;}} " +
            "public fff(){while(true); do{}while(true);int x = 1; " +
                "switch(x){case 1: return; default: return 10;} " +
                "Integer[] yy = {1,2}; for(Integer y: yy){return y;} " +
                "if(x){return 1;}else if(y) {return 10;} else{ return a?1:11;}}");

    private static W_cyclomaticComplexity w = new W_cyclomaticComplexity(c, "");
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
    void call() throws Exception {
        int[] results = {1, 2, 3, 4, 5, 11};
        int j = 0;
        while (i.hasNext()) {
            CtElement ct = (CtElement) i.next();
            if (ct.getClass().getName().contains("Method")) {
                w = new W_cyclomaticComplexity(ct, "");
                int v = w.call().getValue();
                assertEquals(results[j++], v);
            }
        }
    }

}