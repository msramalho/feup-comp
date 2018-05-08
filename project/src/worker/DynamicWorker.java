package worker;

import report.WorkerReport;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.code.CtInvocationImpl;
import spoon.template.TemplateMatcher;
import util.CtIterator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DynamicWorker extends Worker {
    private enum State {
        STANDARD,   // STANDARD means processing elements
        ANY_STMT    // ANY_STMT means processing "any" until the element matches the one after element
    };

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
    public WorkerReport call() throws Exception {
        logger.print("comparing: " + rootNode.toString() + "\n with pattern: " + patternElement.toString() + " - filter is " + getType().getName());

        TemplateMatcher test = new TemplateMatcher(patternElement);

        if (test.matches(rootNode))
            System.out.println("Match");
        else
            System.out.println("No match");

        /*CtIterator pattern = new CtIterator(patternElement);
        CtIterator source = new CtIterator(rootNode);

        CtElement token = (CtElement) pattern.next();

        State state = State.STANDARD;
        while (token != null) {
            logger.print("[" + state + "] PROCESSING: pattern token: " + token.getClass().toString() + " - " + token.toString());

            switch (state) {
                case STANDARD:
                    if (isAny(token)) {
                        logger.print("IS ANY: " + token.toString());
                        state = State.ANY_STMT;
                        token = (CtElement) pattern.next();
                        continue;//jump to next iteration
                    }

                    if (!token.getClass().equals(source.next().getClass())) {
                        logger.print("FAILED PATTERN");
                        return new WorkerReport(0);
                    }
                    token = (CtElement) pattern.next();
                    break;
                case ANY_STMT:
                    CtElement n = (CtElement) source.next();
                    logger.print("CURRENT IS: " + n.getClass() + " - " + n.toString());
                    if (token.getClass().equals(n.getClass())) {
                        logger.print("STOPED CONSUMING ANY");
                        state = State.STANDARD;
                    }
                    break;
            }
        }*/
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
