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
    private Report() { }

    /**
     * Create a report by passing a list of {@link PatternReport}
     *
     * @param patternReports {@link Collection} of {@link PatternReport}
     */
    public static Report fromPatternReports(Collection<PatternReport> patternReports) {
        Report report = new Report();
        for (PatternReport patternReport : patternReports) {
            report.addPatternReport(patternReport);
        }
        return report;
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
        merged.reports.putAll(this.reports); // this beauty works due to HashMapMerger
        merged.reports.putAll(other.reports);
        return merged;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();

        for (Map.Entry<String, PatternReport> entry : reports.entrySet())
            json.addProperty(entry.getKey(), entry.getValue().getValue());

        return json;
    }

    @Override
    public String toString() { return new Gson().toJson(toJson()); }

}
