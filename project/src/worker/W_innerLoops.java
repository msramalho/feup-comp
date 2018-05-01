package worker;

import report.WorkerReport;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.Filter;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.code.CtForEachImpl;

public class W_innerLoops extends Worker {

    public W_innerLoops(CtElement element) {
        super(element);
        logger.print("proof of concept constructor");
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter(CtForEachImpl.class);
    }

    @Override
    public Object call() {
        logger.print("proof of concept run for the ForEachLoops innerLoops Count");
        return new WorkerReport(1, source);
    }
}
