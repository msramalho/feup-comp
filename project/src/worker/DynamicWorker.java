package worker;

import report.WorkerReport;

import spoon.pattern.Match;
import spoon.pattern.Pattern;

import spoon.pattern.PatternBuilder;
import spoon.pattern.Quantifier;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.meta.ContainerKind;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import util.CtIterator;

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
        /*buildPattern(); // build the pattern from the patternElement

        Integer countMatches = pattern.getMatches(rootNode).size();
        if (countMatches >= 1) {
            if (pattern.getMatches(rootNode).get(0).getParameters().getValue("_any_test_") != null) {
            System.out.println(pattern.getMatches(rootNode).get(0).getParameters().getValue("_any_test_") );
            String anyMatch = extractAnyMatch("_any_test_");
            String[] split = rootNode.toString().split(anyMatch);
            System.out.println("I got " + countMatches + " match(es) on " + rootNode + "!!"); }
        }
        return new WorkerReport(countMatches);*/
        Integer newMatches;
        Integer countMatches = 0;
        do {
            buildPattern(); // build the pattern from the patternElement
            newMatches = pattern.getMatches(rootNode).size();

            if (newMatches >= 1) {
                countMatches += newMatches;
                System.out.println("I got " + countMatches + " match(es) on " + rootNode + "!!");

                if (!updateRootNode("_any_test_"))
                    break;
            }
        } while (newMatches != 0);

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
                    pb.parameter("_any_test_").byReferenceName("_any_test_").setMatchingStrategy(Quantifier.RELUCTANT).setContainerKind(ContainerKind.LIST);
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

    /**
     * Não é possível obter o que parou a match do any porque isso é feito internamente e o resultado na match está só: parameter - lista do que tem de items.
     * nao existe tb nenhuma configuração quer permita o matching especial
     * CtElement que é o retornado é também demasiado abrangente sem ter filhos ou pais para que eu posso ir buscar o next sibling
     */
    private boolean updateRootNode(String anyName) {
        List consumedByAny = (List) pattern.getMatches(rootNode).get(0).getParameters().getValue(anyName);
        Match why = pattern.getMatches(rootNode).get(0);
        if (consumedByAny == null)
            return false;

        // Using flag indicating removals to optimize cycle because List is immutable
        int removals = 0;

        // TODO: any idea how to do this any better? without getElements?
        for (CtElement element : rootNode.getElements(null)) {
            for (Object elementConsumed : consumedByAny) {
                if (element.equals(elementConsumed)) {
                    element.delete();
                    removals++;
                    //consumedByAny.remove(elementConsumed);
                }
            }
            if (removals == consumedByAny.size())
                break;
        }
        return true;
    }
}
