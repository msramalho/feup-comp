package worker.workers;

import report.WorkerReport;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import worker.Worker;

/**
 * Count the number of protected fields in a given class (only direct fields of the class, excludes inner classes' fields)
 */
public class W_classFieldsProtected extends Worker {
    public W_classFieldsProtected(CtElement rootNode, String patternName) {
        super(rootNode, patternName);
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter<>(CtClass.class);
    }

    @Override
    public WorkerReport call() {
        return new WorkerReport(
                rootNode.filterChildren(
                        new AbstractFilter<CtField>(CtField.class) {
                            @Override
                            public boolean matches(CtField field) {
                                return field.getModifiers().contains(ModifierKind.PROTECTED) && field.getParent() == rootNode;
                            }
                        }
                ).list().size()
        );
    }
}
