package report;

import java.util.ArrayList;

public class PatternReport {
    String patternName;
    ArrayList<WorkerReport> reports;

    public PatternReport(String patternName) { this.patternName = patternName;}

    public void addReport(WorkerReport report) { reports.add(report); }

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
}
