package report;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class PatternReport {
    //TODO: get pattername from worker factory
    String patternName = "TODO";
    ArrayList<WorkerReport> reports = null;
    ArrayList<Future> futures = new ArrayList<>();

    public PatternReport(String patternName) { this.patternName = patternName;}

    public void addFuture(Future future) { futures.add(future); }

    private void loadReports() {
        if (reports != null) return; // singleton implementation

        reports = new ArrayList<>();
        for (Future f : futures) {
            try {
                reports.add((WorkerReport) f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public Integer sum() {
        loadReports();
        Integer sum = 0;
        for (WorkerReport wReport : reports)
            sum += wReport.getValue();
        return sum;
    }

    public Integer count() {
        loadReports();
        return reports.size();
    }

    //TODO: more statistics methods: Sum, Count, Avg, Std, Mean, ..........
}
