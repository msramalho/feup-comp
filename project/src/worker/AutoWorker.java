package worker;

import spoon.reflect.declaration.CtElement;
import util.Report;

public abstract class AutoWorker extends Worker {
    protected CtElement patternElement;

    public AutoWorker(CtElement element) {
        super(element);
    }

    public void setPatternElement(CtElement patternElement) {
        this.patternElement = patternElement;
    }

    @Override
    public Object call() throws Exception {
        logger.print("called auto worker");
        CtIterator source = new CtIterator(element);
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
