package worker;

import report.WorkerReport;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.code.CtForEachImpl;
import spoon.support.reflect.code.CtLocalVariableImpl;

public class W_innerLoops extends Worker {

    public W_innerLoops(CtElement rootNode, String patternName) {
        super(rootNode, patternName);
        logger.print("proof of concept constructor");
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter(CtLocalVariableImpl.class);
    }

    @Override
    public WorkerReport call() {
        logger.print("proof of concept run for the ForEachLoops innerLoops Count");
        return new WorkerReport(1);
    }
}
