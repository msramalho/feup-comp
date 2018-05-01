package report;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import util.HashMapMerger;

import java.util.Collection;
import java.util.Map;

public class Report {
    private HashMapMerger reports = new HashMapMerger();

    /**
     * Empty constructor for the Report class
     */
    public Report() { }

    /**
     * Create a report by passing a list of {@link PatternReport}
     *
     * @param patterns {@link Collection} of {@link PatternReport}
     */
    public Report(Collection<PatternReport> patterns) {
        for (PatternReport patternReport : patterns)
            addPatternReport(patternReport);
    }

    /**
     * Receive a {@link PatternReport} and add it to the internal list
     *
     * @param patternReport the {@link PatternReport} to add
     */
    private void addPatternReport(PatternReport patternReport) {
        reports.put(patternReport.patternName, patternReport);
    }

    public Report merge(Report other) {
        Report merged = new Report();
        if (reports.size() > 0 && other.reports.size() > 0)
            System.out.println("MERGING REPORTS:\n" + printAll() + "\nAND\n" + other.printAll());

        if (reports.size() > 0 && other.reports.size() > 0)
            System.out.println("--------------------\nresult 1: " + merged.toString());
        merged.reports.putAll(this.reports); // this beauty works due to HashMapMerger

        if (reports.size() > 0 && other.reports.size() > 0)
            System.out.println("--------------------\nresult 2: " + merged.toString());
        merged.reports.putAll(other.reports);

        if (reports.size() > 0 && other.reports.size() > 0)
            System.out.println("--------------------\nresult 3: " + merged.toString());
        return merged;
    }

    private String printAll(){
        String temp = "";
        for (PatternReport p : reports.values()) {
            temp+="\n" + p.patternName + " -> " + p.getValue();
        }
        return temp;
    }

    public Collection<PatternReport> getPatternReports() {
        return reports.values();
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();

        for (Map.Entry<String, PatternReport> entry : reports.entrySet()) {
            // System.out.println(entry.getKey());
            // for (WorkerReport wr : entry.getValue().reports) {
            //     System.out.println("    " + wr.getValue());
            // }
            json.addProperty(entry.getKey(), entry.getValue().getValue());
        }

        return json;
    }

    @Override
    public String toString() { return new Gson().toJson(toJson()); }

}
