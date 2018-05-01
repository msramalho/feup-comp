package report;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Report {
    private ArrayList<PatternReport> reports = new ArrayList<>();
    private HashMap<String, Integer> results = null; // hashmap of PatternName -> PatternResult

    public void addPatternReport(PatternReport patternReport) {
        reports.add(patternReport);
    }

    public HashMap<String, Integer> getResults() {
        if (results != null) return results; // singleton implementation

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
    public String toString() {
        return new Gson().toJson(toJson());
    }
}
