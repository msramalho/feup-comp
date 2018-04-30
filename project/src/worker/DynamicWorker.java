package worker;

import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.AbstractFilter;
import util.CtIterator;
import util.Report;

public class DynamicWorker extends Worker {
    protected CtElement patternElement;

    public DynamicWorker(CtElement patternElement, CtElement rootNode) {
        super(rootNode);
        this.patternElement = patternElement;
    }

    @Override
    protected AbstractFilter setFilter() {
        return null;
    }

    @Override
    public Object call() throws Exception {
        logger.print("called auto worker");
        CtIterator source = new CtIterator(rootNode);
        CtIterator pattern = new CtIterator(patternElement);

        //TODO: this is an example of how the Tree matching algorithm would work with CtIterators
        if (source.hasNext() && pattern.hasNext()) {
            if (source.next() == pattern.next()) {
                logger.print("pattern test iterator found");
                return new Report(1);
            }
        }

        return new Report(0);
    }
}
