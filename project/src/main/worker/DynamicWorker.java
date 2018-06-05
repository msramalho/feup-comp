package worker;

import report.WorkerReport;
import spoon.pattern.Match;
import spoon.pattern.Pattern;
import spoon.pattern.PatternBuilder;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.meta.ContainerKind;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import util.AnyStatement;
import util.CtIterator;
import util.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;

/**
 * This class represents a Worker used for code template matching with Spoon's engine.
 * Exposes some configuration to the user, as seen in the project's user manual.
 * @see <a href="https://github.com/msramalho/feup-comp/blob/master/USAGE.md">User Manual</a>
 */
public class DynamicWorker extends Worker {

    private final CtElement patternElement;
    private Pattern pattern;

    /**
     * Creates a DynamicWorker to start analyse on the given rootNode.
     * Will try to match with the provided patternElement, and will report on the provided patternName.
     * @param rootNode the node to start analyzing on.
     * @param patternName the pattern template.
     * @param patternElement the pattern's name.
     */
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
        buildPattern(); // build the pattern from the patternElement

        Integer countMatches = pattern.getMatches(rootNode).size();
        if (countMatches >= 1) {
            Match test = pattern.getMatches(rootNode).get(0);
            Logger.print(this, "I got " + countMatches + " match(es) on snippet:\n" + rootNode + "\n");
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
                    if (a.getMax() == null) {
                        pb.parameter(a.getName())
                                .byReferenceName(a.getName())
                                .setMatchingStrategy(a.getStrategy())
                                .setContainerKind(ContainerKind.LIST)
                                .setMinOccurence(a.getMin());
                    } else {
                        pb.parameter(a.getName())
                                .byReferenceName(a.getName())
                                .setMatchingStrategy(a.getStrategy())
                                .setContainerKind(ContainerKind.LIST)
                                .setMinOccurence(a.getMin())
                                .setMaxOccurence(a.getMax());
                    }
                }

                for (String v : getPatternMethods(patternElement.toString()))
                    pb.parameter(v).byReferenceName(v).setValueType(CtInvocation.class);
            }).build();
    }

    /**
     * Find instances of variables in the Patterns file in the format _var_SOMETHING_
     *
     * @param code the pattern code as a string
     * @return a {@link HashSet} of {@link String} with the unique variable names
     */
    private HashSet<String> getPatternVariables(String code) {
        return getPatternElements(code, "_var_.*?_");
    }

    /**
     * Find instances of methods in the Patterns file in the format _method_SOMETHING_
     *
     * @param code the pattern code as a string
     * @return a {@link HashSet} of {@link String} with the unique variable names
     */
    private HashSet<String> getPatternMethods(String code) {
        return getPatternElements(code, "_method_.*?_");
    }

    /**
     * Find instances of elements in the Patterns file in the format passed by regex
     *
     * @param code the pattern code as a string
     * @param regex the regex to use in the search of the elements
     * @return a {@link HashSet} of {@link String} with the unique variable names
     */
    private HashSet<String> getPatternElements(String code, String regex) {
        HashSet<String> elements = new HashSet<>();
        Matcher m = java.util.regex.Pattern.compile(regex).matcher(code);
        while (m.find()) elements.add(m.group(0));
        return elements;
    }

    /**
     * Find all instances of any statements in the Patterns file in the format _<strategy>_any_[minXX_][maxXX_]
     *
     * @param code the pattern code as a string
     * @return a {@link HashMap} of {@link String} with the unique any names associated to the any settings
     */
    private HashMap<String, AnyStatement> getPatternAnys(String code) {
        HashMap<String, AnyStatement> anys = new HashMap<>();
        Matcher m = java.util.regex.Pattern
                .compile("_(lazy|greedy)_any_(?:(min(\\d+)_)?(max(\\d+)_)?)?")
                .matcher(code);

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

}
