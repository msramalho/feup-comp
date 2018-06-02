package worker.workers;

import report.WorkerReport;
import spoon.reflect.declaration.*;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import worker.Worker;

/**
 * Count the number of direct methods defined in a given Class (does not include constructors nor methods belonging to sub-classes)
 */
public class W_countMethods extends Worker {
    public W_countMethods(CtElement rootNode, String patternName) {
        super(rootNode, patternName);
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter(CtClass.class);
    }

    @Override
    public WorkerReport call() throws Exception {
        //TODO: make this pattern use a sum/average
        return new WorkerReport(
                rootNode.filterChildren(new AbstractFilter<CtMethod>(CtMethod.class) {
                    @Override
                    public boolean matches(CtMethod method) {
                        return method.getParent() == rootNode; // guarantee this is a direct child
                    }
                }).list().size() // returning the number of methods
        );
    }
}
