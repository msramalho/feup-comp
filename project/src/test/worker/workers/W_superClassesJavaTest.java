package worker.workers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import report.WorkerReport;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.support.reflect.code.CtBlockImpl;

import static org.junit.jupiter.api.Assertions.*;

class W_superClassesJavaTest {
    private static CtClass c = Launcher.parseClass("class A extends java.util.HashMap { void m() { System.out.println(\"yeah\");} private String s(){return \"\";} public int ii(){ return 10;} protected double pp(){ return 0.0;} abstract fff();");
    private static CtClass c2 = Launcher.parseClass("class A extends B implements C { void m() { System.out.println(\"yeah\");} private String s(){return \"\";} public int ii(){ return 10;} protected double pp(){ return 0.0;} abstract fff();");



    private static W_superClassesJava w = new W_superClassesJava(c, "");
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
        assertEquals((new WorkerReport(6)).getValue(), w.call().getValue());
        w = new W_superClassesJava(c2, "");
        assertEquals((new WorkerReport(2)).getValue(), w.call().getValue());
    }

}