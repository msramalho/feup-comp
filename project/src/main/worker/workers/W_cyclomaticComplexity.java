package worker.workers;

import report.WorkerReport;
import spoon.reflect.code.*;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import util.CtIterator;
import worker.Worker;

/**
 * Calculates the cyclomatic complexity for a given method (includes if, else, ternary, for, foreach, while, switch-case, switch-default)
 */
public class W_cyclomaticComplexity extends Worker {
    public W_cyclomaticComplexity(CtElement rootNode, String patternName) {
        super(rootNode, patternName);
    }

    @Override
    protected AbstractFilter setFilter() {
        return new TypeFilter<>(CtMethod.class);
    }

    /**
     * Iterates the current method in DFS and looks for elements that count toward the cyclomatic complexity metric
     *
     * @return WorkerReport
     * @throws Exception
     */
    @Override
    public WorkerReport call() throws Exception {
        Integer complexity = 0;

        CtIterator it = new CtIterator(rootNode);
        while (it.hasNext()) {
            CtElement c = (CtElement) it.next();
            if (c instanceof CtIf) {
                complexity++; // increment once for every if
                // increment once for every else that won't be triggered as an if (so as not to duplicate)
                CtStatement ctElse = ((CtIf) c).getElseStatement();
                if (ctElse != null && !ctElse.toString().startsWith("if")) complexity++;

            } else if (c instanceof CtConditional) {
                complexity++; // the else is detected as an expression
                CtExpression ctElse = ((CtConditional) c).getElseExpression();
                if (ctElse != null) complexity++;

            } else if (c instanceof CtFor) complexity++;
            else if (c instanceof CtForEach) complexity++;
            else if (c instanceof CtWhile) complexity++;
            else if (c instanceof CtDo) complexity++;
            else if (c instanceof CtCase) complexity++; // defaults are also CtCase
        }
        return new WorkerReport(complexity);
    }
}
