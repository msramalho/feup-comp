package worker.workers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import util.CtIterator;

import static org.junit.jupiter.api.Assertions.*;

class W_linesOfCodeMethodTest {
    private static CtClass c = Launcher.parseClass("abstract class A { void m() { System.out.println(\"yeah\");} private String s(){return \"\";} public int ii(){ int a; return 10;} protected double pp(){ return 0.0;} abstract fff();");


    private static W_linesOfCodeMethod w = new W_linesOfCodeMethod(c, "");
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
        int[] results = {3, 3, 4, 3, 1};
        int j = 0;
        while (i.hasNext()) {
            CtElement ct = (CtElement) i.next();
            if (ct.getClass().getName().contains("Method")) {
                w = new W_linesOfCodeMethod(ct, "");
                int v = w.call().getValue();
                assertEquals(results[j++], v);
            }
        }
    }

}