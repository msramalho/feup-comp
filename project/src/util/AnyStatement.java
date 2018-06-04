package util;

import spoon.pattern.Quantifier;

public class AnyStatement {

    private int min = 0;
    private Integer max = null;
    private Quantifier strategy;
    private String name;

    public AnyStatement(String strat, String name) {
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