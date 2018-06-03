package report;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import util.HashMapMerger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Report {
    private HashMap<String, PatternReport> reports;

    /**
     * Empty constructor for the Report class
     */
    public Report() {
        reports = new HashMapMerger();
    }

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
        reports.put(patternReport.getPatternName(), patternReport);
    }

    public Report merge(Report other) {
        Report merged = new Report();
        merged.reports.putAll(this.reports); // this beauty works due to HashMapMerger
        merged.reports.putAll(other.reports);
        return merged;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();

        for (Map.Entry<String, PatternReport> patternEntry : reports.entrySet()) {
            for (Map.Entry<String, Number> resultEntry : patternEntry.getValue().getOperationsResults().entrySet()) {
                String name = patternEntry.getKey() + "_" + resultEntry.getKey();
                json.addProperty(name, resultEntry.getValue());
            }
        }

        return json;
    }

    @Override
    public String toString() { return new Gson().toJson(this.toJson()); }

}
