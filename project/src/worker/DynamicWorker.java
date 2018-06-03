package worker;

import report.WorkerReport;
import spoon.pattern.PatternBuilder;
import spoon.pattern.Quantifier;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.meta.ContainerKind;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import util.CtIterator;

import spoon.pattern.Pattern;

import java.util.HashSet;
import java.util.List;
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
            if (pattern.getMatches(rootNode).get(0).getParameters().getValue("_any_test_") != null) {
            System.out.println(pattern.getMatches(rootNode).get(0).getParameters().getValue("_any_test_") );
            List strings = (List) pattern.getMatches(rootNode).get(0).getParameters().getValue("_any_test_");
            String anyMatch = extractAnyMatch("_any_test_");
            String[] split = rootNode.toString().split(anyMatch);
            System.out.println("I got " + countMatches + " match(es) on " + rootNode + "!!"); }
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
                    pb.parameter("_any_test_").byReferenceName("_any_test_").setMatchingStrategy(Quantifier.GREEDY).setContainerKind(ContainerKind.LIST);
                    pb.parameter("_any_cenas_").byReferenceName("_any_cenas_").setMatchingStrategy(Quantifier.RELUCTANT).setContainerKind(ContainerKind.LIST);
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

    /*private boolean isAny(CtElement e) {
        //TODO: improve this if someone knows how to get the method name :'(
        if (e instanceof CtInvocationImpl) {
            Pattern p = Pattern.compile("_any_()");
            Matcher m = p.matcher(e.toString());
            return m.find();
        }
        return false;
    }*/

    private String extractAnyMatch(String anyName) {
        List tempList = (List) pattern.getMatches(rootNode).get(0).getParameters().getValue(anyName);

        StringBuilder result = new StringBuilder();
        for (Object stmt: tempList)
            result.append("\\Q").append(stmt).append(";\\E\\s*");

        return result.toString();
    }
}
