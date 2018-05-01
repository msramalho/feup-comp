package worker;

import report.WorkerReport;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.AbstractFilter;
import util.CtIterator;

public class DynamicWorker extends Worker {
    protected CtElement patternElement;

<<<<<<< HEAD:project/src/worker/DynamicWorker.java
    public DynamicWorker(CtElement patternElement, CtElement rootNode) {
        super(rootNode);
        this.patternElement = patternElement;
=======
    public AutoWorker(CtElement source) {
        super(source);
>>>>>>> issue-2:project/src/worker/AutoWorker.java
    }

    @Override
    protected AbstractFilter setFilter() {
        return null;
    }

    @Override
    public Object call() throws Exception {
        logger.print("called auto worker");
<<<<<<< HEAD:project/src/worker/DynamicWorker.java
        CtIterator source = new CtIterator(rootNode);
        CtIterator pattern = new CtIterator(patternElement);
=======
        CtIterator source_it = new CtIterator(this.source);
        CtIterator pattern_it = new CtIterator(patternElement);
>>>>>>> issue-2:project/src/worker/AutoWorker.java

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
