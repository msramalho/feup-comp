package report;

import spoon.reflect.declaration.CtElement;

public class WorkerReport {
    Integer value;
    CtElement source;

    public WorkerReport(Integer value, CtElement source) {
        this.value = value;
        this.source = source;
    }

    public Integer getValue() {
        return value;
    }
}
