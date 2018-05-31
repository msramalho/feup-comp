package worker;

import report.WorkerReport;
import spoon.pattern.PatternBuilder;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import util.CtIterator;

import spoon.pattern.Pattern;


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
    public WorkerReport call() throws Exception {
        // logger.print("comparing: " + rootNode.toString() + "\n with pattern: " + patternElement.toString() + " - filter is " + getType().getName());

        Pattern pattern = PatternBuilder.create(patternElement).configureParameters(
                pb -> {
                    pb.parameter("_var_x_").byVariable("_var_x_");
                    pb.parameter("_var_y_").byVariable("_var_y_");
                    pb.parameter("_var_z_").byVariable("_var_z_");
                })
                .build();

        Integer countMatches = pattern.getMatches(rootNode).size();
        if (countMatches >= 1) {
            System.out.println("I got " + countMatches + " match(es) on " + rootNode + "!!");
        }
        return new WorkerReport(countMatches);
    }

    /*private boolean isAny(CtElement e) {
        //TODO: improve this if someone knows how to get the method name :'(
        if (e instanceof CtInvocationImpl) {
            Pattern p = Pattern.compile("_any_()");
            Matcher m = p.matcher(e.toString());
            return m.find();
        }
        return false;
    }*/
}
