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
 * Counts the number of super classes of a given class (includes java defaults)
 */
public class W_superClassesJava extends Worker {
    public W_superClassesJava(CtElement rootNode, String patternName) {
        super(rootNode, patternName);
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter<>(CtClassImpl.class);
    }

    @Override
    public WorkerReport call() throws Exception {
        List<CtTypeReference<?>> typeResult = rootNode.map(new SuperInheritanceHierarchyFunction().includingSelf(false).returnTypeReferences(true)).list();
        return new WorkerReport(typeResult.size() - 1);
    }
}
