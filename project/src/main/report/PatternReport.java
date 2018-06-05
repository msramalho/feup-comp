package report;

import util.Operable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Holds the aggregate reports of Workers trying to match a given pattern.
 */
public class PatternReport extends Operable {
    private String patternName;
    public ArrayList<WorkerReport> reports = new ArrayList<>();
    private Map<String, Function<Stream<WorkerReport>, ? extends Number>> operations = new HashMap<>();


    public PatternReport(String patternName) {
        this.patternName = patternName;
    }

    public void addWorkerReport(WorkerReport report) { reports.add(report); }

    public void addOperation(String name, Function<Stream<WorkerReport>, Number> op) {
        operations.put(name, op);
    }

    public void addOperations(Map<String, Function<Stream<WorkerReport>, Number>> patternOps) {
        for (Map.Entry<String, Function<Stream<WorkerReport>, Number>> entry : patternOps.entrySet()) {
            operations.putIfAbsent(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Gets the Mapped results of operation_name -> operation_result.
     * Performs this PatternReport's operations along this pattern report's WorkerReports.
     * @return the mapped results.
     */
    public Map<String, Number> getOperationsResults() {
        Map<String, Number> result = new HashMap<>();
        for (Map.Entry<String, Function<Stream<WorkerReport>, ? extends Number>> entry : operations.entrySet()) {
            result.put(entry.getKey(), this.accept(entry.getValue()));
        }
        return result;
    }

    /**
     * Merges two PatternReports and their corresponding WorkerReports.
     * @param other the other PatternReport.
     * @return the merged PatternReport.
     */
    public PatternReport merge(PatternReport other) {
        PatternReport merged = new PatternReport(patternName);

        // Operations must be common among all workers of a given pattern
        merged.operations = this.operations;

        merged.reports.addAll(this.reports);
        merged.reports.addAll(other.reports);
        return merged;
    }

    public String getPatternName() {
        return patternName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PatternReport that = (PatternReport) o;

        return patternName != null ? patternName.equals(that.patternName) : that.patternName == null;
    }

    @Override
    public int hashCode() {
        return patternName != null ? patternName.hashCode() : 0;
    }

    @Override
    protected Stream<WorkerReport> getStream() {
        return reports.stream();
    }

}
