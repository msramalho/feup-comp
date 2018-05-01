package report;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import worker.WorkerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Report {
    private ArrayList<PatternReport> reports = new ArrayList<>();
    private HashMap<String, Integer> results = null; // hashmap of PatternName -> PatternResult

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
        reports.add(patternReport);
        results = null; // to force restart the singleton
    }

    private HashMap<String, Integer> getResults() {
        if (results != null) return results; // singleton implementation

        results = new HashMap<>();
        //TODO: apply desired operation here: sum, count, avg, ...or list of operations
        for (PatternReport report : reports)
            results.put(report.patternName, report.sum());

        return results;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();

        for (Map.Entry<String, Integer> res : getResults().entrySet())
            json.addProperty(res.getKey(), res.getValue());

        return json;
    }

    @Override
    public String toString() { return new Gson().toJson(toJson()); }
}
