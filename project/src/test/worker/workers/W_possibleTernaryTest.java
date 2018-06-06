package worker.workers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.support.reflect.code.CtBlockImpl;
import spoon.support.reflect.code.CtLocalVariableImpl;
import util.CtIterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class W_possibleTernaryTest {
    private static CtClass c = Launcher.parseClass("class A { " +
            "    public void test() {\n" +
            "        int i = 0;\n" +
            "        System,.out.println(\"testando\");\n" +
            "        bool test = true;\n" +
            "        if (test) {\n" +
            "            i = 1;\n" +
            "        } else  {\n" +
            "            i = 2;\n" +
            "        }\n" +
            "    }\n}");

    private static W_possibleTernary w = new W_possibleTernary(c, "");
    private static CtIterator i;

    @BeforeEach
    void setUp() {
        w.filter = w.setFilter();
        i = new CtIterator(c);
    }

    @Test
    void setFilter() {
        assertTrue(w.filter.matches(new CtLocalVariableImpl()));
        assertFalse(w.filter.matches(new CtBlockImpl()));
    }

    @Test
    void call() {
        int result = 1;
        int workerResult= 0;
        while (i.hasNext()) {
            CtElement ct = (CtElement) i.next();
            if (ct.getClass().equals(CtLocalVariableImpl.class)) {
                w = new W_possibleTernary(ct, "");
                workerResult += w.call().getValue();
            }
        }
        assertEquals(result, workerResult);
    }
}
