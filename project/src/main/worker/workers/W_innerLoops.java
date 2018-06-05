package worker.workers;

import report.WorkerReport;
import spoon.reflect.code.CtDo;
import spoon.reflect.code.CtFor;
import spoon.reflect.code.CtForEach;
import spoon.reflect.code.CtWhile;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.CtScanner;
import spoon.reflect.visitor.CtVisitor;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.code.CtLocalVariableImpl;
import worker.Worker;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Counts the depth of loops inside a Method + test
 */
public class W_innerLoops extends Worker {

    public W_innerLoops(CtElement rootNode, String patternName) {
        super(rootNode, patternName);
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter<>(CtMethod.class);
    }

    @Override
    public WorkerReport call() {
        AtomicInteger counter = new AtomicInteger(1);
        AtomicInteger max = new AtomicInteger(0);
        CtScanner scanner = new CtScanner() {

            private boolean isLoop(CtElement e) {
                return e instanceof CtFor || e instanceof CtForEach || e instanceof CtWhile || e instanceof CtDo;
            }

            @Override
            protected void enter(CtElement e) {
                super.enter(e);
                if (isLoop(e)) counter.incrementAndGet();
            }

            @Override
            protected void exit(CtElement e) {
                super.exit(e);
                if (isLoop(e)) {
                    counter.decrementAndGet();
                    max.set(Math.max(counter.get(), max.get()));
                }
            }
        };
        rootNode.accept(scanner);
        return new WorkerReport(max.get());
    }
}
