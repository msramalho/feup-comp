package report;

public class WorkerReport implements Comparable<WorkerReport> {
    // TODO: may need to save more information in the future
    Integer value;

    public WorkerReport(Integer value) {
        this.value = value;
    }

    public Integer getValue() { return value; }

    @Override
    public int compareTo(WorkerReport o) {
        if (this.getValue() < o.getValue()) return -1;
        else if (this.getValue() > o.getValue()) return 1;
        return 0;
    }
}
