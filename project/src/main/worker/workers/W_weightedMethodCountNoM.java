package worker.workers;

import report.WorkerReport;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import worker.Worker;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Calculates the Weighted Method Count (WMC) for the Number of Methods of a Class
 */
public class W_weightedMethodCountNoM extends Worker {
    public W_weightedMethodCountNoM(CtElement rootNode, String patternName) {
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
            W_classMethods w = new W_classMethods((CtElement) m, "");
            try {
                Integer v = w.call().getValue();
                wmcRef.getAndAdd(v);
            } catch (Exception e) {
                System.err.println("Unable to collect the Number of Methods value from " + m.toString());
                e.printStackTrace();
            }
        });
        return new WorkerReport(wmcRef.get());
    }
}
