package report;

import util.Operable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class PatternReport extends Operable {
    private String patternName;

    private ArrayList<WorkerReport> reports = new ArrayList<>();

    private Map<String, Function<Stream<WorkerReport>, ? extends Number>> operations = new HashMap<>();

    public PatternReport(String patternName) {
        this.patternName = patternName;
        addOperation("count", Stream::count);
    }

    public void addWorkerReport(WorkerReport report) { reports.add(report); }

    public void addOperation(String name, Function<Stream<WorkerReport>, Number> op) {
        operations.put(name, op);
    }

    public Map<String, Number> getOperationsResults() {
        Map<String, Number> result = new HashMap<>();
        for (Map.Entry<String, Function<Stream<WorkerReport>, ? extends Number>> entry : operations.entrySet()) {
            result.put(entry.getKey(), this.accept(entry.getValue()));
        }
        return result;
    }

    public PatternReport merge(PatternReport other) {
        PatternReport merged = new PatternReport(patternName);
        merged.reports.addAll(this.reports);
        merged.reports.addAll(other.reports);
        return merged;
    }

    public String getPatternName() {
        return patternName;
    }

    //TODO: more statistics methods: Sum, Count, Avg, etc. --> implemented by Stream methods, Stream::sum, ::avg, etc.

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
