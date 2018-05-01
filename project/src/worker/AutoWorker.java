package worker;

import report.WorkerReport;
import spoon.reflect.declaration.CtElement;
import util.CtIterator;

public abstract class AutoWorker extends Worker {
    protected CtElement patternElement;

    public AutoWorker(CtElement source) {
        super(source);
    }

    public void setPatternElement(CtElement patternElement) {
        this.patternElement = patternElement;
    }

    @Override
    public Object call() throws Exception {
        logger.print("called auto worker");
        CtIterator source_it = new CtIterator(this.source);
        CtIterator pattern_it = new CtIterator(patternElement);

        //TODO: this is an example of how the Tree matching algorithm would work with CtIterators
        if (source_it.hasNext() && pattern_it.hasNext()) {
            if (source_it.next() == pattern_it.next()) {
                logger.print("pattern test iterator found");
                return new WorkerReport(1, source);
            }
        }

        return new WorkerReport(0, source);
    }
}
