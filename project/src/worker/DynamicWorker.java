package worker;

import report.WorkerReport;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import util.CtIterator;

public class DynamicWorker extends Worker {
    private CtElement patternElement;

    public DynamicWorker(CtElement rootNode, String patternName, CtElement patternElement) {
        super(rootNode, patternName);
        this.patternElement = patternElement;
        loadFilter();
    }

    @Override
    protected AbstractFilter setFilter() {
        CtIterator iterator = new CtIterator(patternElement);
        return new TypeFilter(iterator.next(1).getClass());
    }

    @Override
    public Object call() throws Exception {
        logger.print("called auto worker");
        CtIterator source = new CtIterator(rootNode);
        CtIterator pattern = new CtIterator(patternElement);
        CtIterator source_it = new CtIterator(this.rootNode);
        CtIterator pattern_it = new CtIterator(patternElement);

        //TODO: this is an example of how the Tree matching algorithm would work with CtIterators
        if (source_it.hasNext() && pattern_it.hasNext()) {
            if (source_it.next() == pattern_it.next()) {
                logger.print("pattern test iterator found");
                return new WorkerReport(1);
            }
        }

        return new WorkerReport(0);
    }
}
