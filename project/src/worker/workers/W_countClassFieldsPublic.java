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
 * Count the number of public fields in a given class
 */
public class W_countClassFieldsPublic extends Worker {
    public W_countClassFieldsPublic(CtElement rootNode, String patternName) {
        super(rootNode, patternName);
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter<>(CtClass.class);
    }

    @Override
    public WorkerReport call() throws Exception {
        return new WorkerReport(
                rootNode.filterChildren(
                        new AbstractFilter<CtField>(CtField.class) {
                            @Override
                            public boolean matches(CtField field) {
                                return field.getModifiers().contains(ModifierKind.PUBLIC) && field.getParent() == rootNode;
                            }
                        }
                ).list().size()
        );
    }
}
