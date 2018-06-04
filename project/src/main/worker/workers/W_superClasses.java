package worker.workers;

import report.WorkerReport;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.declaration.CtClassImpl;
import worker.Worker;

import java.util.List;

/**
 * Counts the number of super classes of a given class that are not in "java.*" packages (so as to include only user-defined or user-used class inheritance)
 */
public class W_superClasses extends Worker {
    public W_superClasses(CtElement rootNode, String patternName) {
        super(rootNode, patternName);
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter<>(CtClassImpl.class);
    }

    @Override
    public WorkerReport call() throws Exception {
        List<CtTypeReference<?>> typeResult = rootNode.map(new SuperInheritanceHierarchyFunction().includingSelf(false).returnTypeReferences(true)).list();
        Integer counter = typeResult.size();
        for (CtTypeReference<?> a : typeResult)
            if (a.toString().matches("java\\..*")) counter--;

        return new WorkerReport(counter);
    }
}
