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
    public WorkerReport call() {
        return new WorkerReport(rootNode.filterChildren(new AbstractFilter<CtMethod>(CtMethod.class) {
            @Override
            public boolean matches(CtMethod method) {
                return method.getParent() == rootNode; // guarantee this is a direct child
            }
        }).list().size());
    }
}
