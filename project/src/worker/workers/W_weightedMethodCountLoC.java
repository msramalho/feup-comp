package worker.workers;

import report.WorkerReport;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import worker.Worker;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class W_weightedMethodCountLoC extends Worker {
    public W_weightedMethodCountLoC(CtElement rootNode, String patternName) {
        super(rootNode, patternName);
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter<>(CtClass.class);
    }

    @Override
    public WorkerReport call() throws Exception {
        final AtomicInteger wmcRef = new AtomicInteger(); //reference needed for lambda function
        rootNode.filterChildren(new AbstractFilter<CtMethod>(CtMethod.class) {
            @Override
            public boolean matches(CtMethod method) {
                return method.getParent() == rootNode; // guarantee this is a direct child
            }
        }).forEach(m -> {
            W_linesOfCodeMethod w = new W_linesOfCodeMethod((CtElement) m, "");
            try {
                Integer v = w.call().getValue();
                wmcRef.getAndAdd(v);
            } catch (Exception e) {
                System.err.println("Unable to collect the Lines of Code value from " + m.toString());
                e.printStackTrace();
            }
        });
        return new WorkerReport(wmcRef.get());
    }
}
