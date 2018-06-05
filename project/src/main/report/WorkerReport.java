package report;

/**
 * Holds the result of a Worker's execution.
 * Can be extended to hold more information (e.g. the line in which the worker was triggered).
 */
public class WorkerReport implements Comparable<WorkerReport> {
    Integer value;

    public WorkerReport(Integer value) {
        this.value = value;
    }

    public Integer getValue() { return value; }

    public Long getLongValue() { return Long.valueOf(getValue()); }

    @Override
    public int compareTo(WorkerReport o) {
        if (this.getValue() < o.getValue()) return -1;
        else if (this.getValue() > o.getValue()) return 1;
        return 0;
    }
}
