package report;

import util.Operable;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Stream;

public class PatternReport extends Operable {
    private String patternName;

    private ArrayList<WorkerReport> reports = new ArrayList<>();

    private Function<Stream<WorkerReport>, ? extends Number> operation = Stream::count;

    public PatternReport(String patternName) {
        this.patternName = patternName;
    }

    public PatternReport(String patternName, Function<Stream<WorkerReport>, Number> operation) {
        this.patternName = patternName;
        this.operation = operation;
    }

    public void addWorkerReport(WorkerReport report) { reports.add(report); }

    public Number getValue() {
//        return this.accept((Stream<WorkerReport> reportStream) -> reportStream.count());
        return this.accept(this.operation);
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
