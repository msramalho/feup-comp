package worker;

import report.WorkerReport;

import spoon.pattern.ParametersBuilder;
import spoon.pattern.Pattern;

import spoon.pattern.PatternBuilder;
import spoon.pattern.Quantifier;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.meta.ContainerKind;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import util.CtIterator;

import java.util.*;
import java.util.regex.Matcher;


public class DynamicWorker extends Worker {

    private CtElement patternElement;
    private Pattern pattern;

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
    public WorkerReport call() {
        // logger.print("comparing: " + rootNode.toString() + "\n with pattern: " + patternElement.toString() + " - filter is " + getType().getName());
        buildPattern(); // build the pattern from the patternElement

        Integer countMatches = pattern.getMatches(rootNode).size();
        if (countMatches >= 1) {
            System.out.println("I got " + countMatches + " match(es) on " + rootNode + "!!");
        }
        return new WorkerReport(countMatches);
    }


    /**
     * When the Dynamic worker is called, the pattern gets built so that it can be used for matching
     */
    private void buildPattern() {
        pattern = PatternBuilder.create(patternElement).configureParameters(
            pb -> {
                for (String v : getPatternVariables(patternElement.toString()))
                    pb.parameter(v).byVariable(v);

                for (AnyStatement a : getPatternAnys(patternElement.toString()).values()) {
                    if (a.getMax() == null)
                        pb.parameter(a.getName()).byReferenceName(a.getName()).setMatchingStrategy(a.getStrategy()).setContainerKind(ContainerKind.LIST).setMinOccurence(a.getMin());
                    else pb.parameter(a.getName()).byReferenceName(a.getName()).setMatchingStrategy(a.getStrategy()).setContainerKind(ContainerKind.LIST).setMinOccurence(a.getMin()).setMaxOccurence(a.getMax());
                }
            }).build();
    }

    /**
     * Find instances of variables in the Patterns file in the format _var_SOMETHING_
     *
     * @param code the pattern code as a string
     * @return a {@link HashSet} of {@link String} with the unique variable names
     */
    private HashSet<String> getPatternVariables(String code) {
        HashSet<String> variables = new HashSet<>();
        Matcher m = java.util.regex.Pattern.compile("_var_.*?_").matcher(code);
        while (m.find()) variables.add(m.group(0));
        return variables;
    }

    private HashMap<String, AnyStatement> getPatternAnys(String code) {
        HashMap<String, AnyStatement> anys = new HashMap<>();
        Matcher m = java.util.regex.Pattern.compile("_(lazy|greedy)_any_(?:(min(\\d+)_)?(max(\\d+)_)?)?").matcher(code);

        while (m.find()) {
            AnyStatement any = new AnyStatement(m.group(1), m.group(0));
            if (m.group(2) != null)
                any.setMin(m.group(3));
            if (m.group(4) != null)
                any.setMax(m.group(5));
            anys.put(m.group(0), any);
        }

        return anys;
    }

    public class AnyStatement {

        private int min = 0;
        private Integer max = null;
        private Quantifier strategy;
        private String name;

        AnyStatement(String strat, String name) {
            strategy = strat.equals("lazy") ? Quantifier.RELUCTANT : Quantifier.GREEDY;
            this.name = name;
        }

        public void setMin(String value) {
            min = Integer.parseInt(value);
        }

        public void setMax(String value) {
            max = Integer.parseInt(value);
        }

        public Integer getMax() {
            return max;
        }

        public int getMin() {
            return min;
        }

        public Quantifier getStrategy() {
            return strategy;
        }

        public String getName() {
            return name;
        }

    }

}
