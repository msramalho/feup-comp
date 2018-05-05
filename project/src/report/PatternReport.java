package report;

import java.util.ArrayList;

public class PatternReport {
    String patternName;
    // Processor processor; // Processor that properly returns the result of this Pattern
    ArrayList<WorkerReport> reports = new ArrayList<>();

    public PatternReport(String patternName) {
        this.patternName = patternName;
    }

    public void addWorkerReport(WorkerReport report) { reports.add(report); }

    public Integer getValue() {
        return sum();
    }

    public PatternReport merge(PatternReport other) {
        PatternReport merged = new PatternReport(patternName);
        merged.reports.addAll(this.reports);
        merged.reports.addAll(other.reports);
        return merged;
    }

    // Data handling functions for the report

    // TODO: maybe refactor this into a new class
    public Integer sum() {
        Integer sum = 0;
        for (WorkerReport wReport : reports)
            sum += wReport.getValue();
        return sum;
    }

    public Integer count() { return reports.size(); }


    //TODO: more statistics methods: Sum, Count, Avg, Std, Mean, ..........


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
}
