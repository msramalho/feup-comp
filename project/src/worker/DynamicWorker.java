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

        TemplateMatcher matcher = new TemplateMatcher(patternElement);

        /*for (CtElement t : rootNode.getElements(null)) {
            System.out.println(t.getClass() + " ---- " + t.toString());
        }*/

        if (matcher.matches(rootNode))
            System.out.println("DEU MATCH BOYSSS - " + rootNode.toString());
        else
            System.out.println("ripperinooooooo");

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
