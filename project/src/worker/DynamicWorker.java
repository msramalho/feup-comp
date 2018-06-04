package worker;

import report.WorkerReport;

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

    public enum ParameterType {
        VAR, ANY, METHOD
    }

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
                    getPatternAnys(patternElement.toString());
                    pb.parameter("_lazy_any_").byReferenceName("_lazy_any_").setMatchingStrategy(Quantifier.RELUCTANT).setContainerKind(ContainerKind.LIST);
                    //pb.parameter("_any_cenas_").byReferenceName("_any_cenas_").setMatchingStrategy(Quantifier.RELUCTANT).setContainerKind(ContainerKind.LIST);
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

    /*private EnumMap<ParameterType, ArrayList<String>> getPatternParameters(String code) {
        EnumMap<ParameterType, ArrayList<String>> parameters = new EnumMap<>(ParameterType.class);
        for (int i = 0; i < ParameterType.values().length; ++i ) {
            parameters.put(ParameterType.values()[i], new ArrayList<String>());
        }

        return parameters;
    }*/

    private HashSet<String> getPatternAnys(String code) {
        HashSet<String> anys = new HashSet<>();
        Matcher m = java.util.regex.Pattern.compile("_(lazy|greedy)_any_(?:(min(\\d+)_)?(max(\\d+)_)?)?").matcher(code);
        while (m.find())
            anys.add(m.group(0));

        return anys;
    }

    public class AnyStatement {

        private int min = 0;
        private Integer max = null;
        private Quantifier strategy;

        AnyStatement(String strat) {
            extractStrategy(strat);
        }

        AnyStatement(String strat, String limit) {
            extractStrategy(strat);
            extractLimit(limit);
        }

        AnyStatement(String strat, String min, String max) {
            extractStrategy(strat);
            extractLimits(min, max);
        }

        private void extractStrategy(String strat) {
            strategy = strat.equals("lazy") ? Quantifier.RELUCTANT : Quantifier.GREEDY;
        }

        private void extractLimit(String limit) {
            String lim = limit.substring(0, 3);
            if (lim.equals("min"))
                min = Integer.parseInt(limit.substring(3, limit.length()));
            else
                max = Integer.parseInt(limit.substring(3, limit.length()));
        }

        private void extractLimits(String min, String max) {
            this.min = Integer.parseInt(min.substring(3, min.length()));
            this.max = Integer.parseInt(max.substring(3, max.length()));
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
    }

}
