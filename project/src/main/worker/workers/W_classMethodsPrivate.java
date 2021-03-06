package worker.workers;

import report.WorkerReport;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import worker.Worker;

/**
 * Count the number of private direct methods defined in a given Class (does not include constructors nor methods belonging to sub-classes)
 */
public class W_classMethodsPrivate extends Worker {
    public W_classMethodsPrivate(CtElement rootNode, String patternName) {
        super(rootNode, patternName);
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter<>(CtClass.class);
    }

    @Override
    public WorkerReport call() {
        return new WorkerReport(
                rootNode.filterChildren(new AbstractFilter<CtMethod>(CtMethod.class) {
                    @Override
                    public boolean matches(CtMethod method) {
                        // guarantee this is a direct child and that the method is private
                        return method.getParent() == rootNode && method.getModifiers().contains(ModifierKind.PRIVATE);
                    }
                }).list().size() // returning the number of methods
        );
    }
}
