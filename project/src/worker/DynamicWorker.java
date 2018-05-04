package worker;

import report.WorkerReport;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.code.CtInvocationImpl;
import util.CtIterator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DynamicWorker extends Worker {
    private CtElement patternElement;

    public DynamicWorker(CtElement rootNode, String patternName, CtElement patternElement) {
        super(rootNode, patternName);
        CtIterator iterator = new CtIterator(patternElement);
        this.patternElement = (CtElement) iterator.next(1);
        loadFilter();
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter(patternElement.getClass());
    }

    @Override
    public Object call() throws Exception {
        logger.print("comparing: " + rootNode.toString() + "\n with pattern: " + patternElement.toString() + " - filter is " + getType().getName());

        /**
         * State Machine states
         * 0 means processing elements
         * 1 means processing "any" until the element matches the one after element
         */
        int STATE = 0;

        CtIterator pattern = new CtIterator(patternElement);
        CtIterator source = new CtIterator(rootNode);

        CtElement token = (CtElement) pattern.next();

        while (token != null) {
            logger.print("[" + STATE + "] PROCESSING: pattern token: " + token.getClass().toString() + " - " + token.toString());

            switch (STATE) {
                case 0:
                    if (isAny(token)) {
                        logger.print("IS ANY: " + token.toString());
                        STATE = 1;
                        token = (CtElement) pattern.next();
                        continue;//jump to next iteration
                    }

                    if (!token.getClass().equals(source.next().getClass())) {
                        logger.print("FAILED PATTERN");
                        return new WorkerReport(0);
                    }
                    token = (CtElement) pattern.next();
                    break;
                case 1:
                    CtElement n = (CtElement) source.next();
                    logger.print("CURRENT IS: " + n.getClass() + " - " + n.toString());
                    if (token.getClass().equals(n.getClass())) {
                        logger.print("STOPED CONSUMING ANY");
                        STATE = 0;
                    }
                    break;
            }
        }
        return new WorkerReport(1);
    }

    private boolean isAny(CtElement e) {
        //TODO: improve this if someone knows how to get the method name :'(
        if (e instanceof CtInvocationImpl) {
            Pattern p = Pattern.compile("_any_()");
            Matcher m = p.matcher(e.toString());
            return m.find();
        }
        return false;
    }
}
