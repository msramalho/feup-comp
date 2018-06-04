package util;

import report.WorkerReport;

import java.util.stream.Stream;

public class OperationsTest {

    public static void main(String[] args) {

        testMedian();
        testCount();
        testStandardDeviation();

    }

    static WorkerReport[] workerReports = {
            new WorkerReport(0),
            new WorkerReport(1),
            new WorkerReport(1),
            new WorkerReport(2),
            new WorkerReport(3),
            new WorkerReport(4),
    };

    static void testMedian() {
        Stream<WorkerReport> s = Stream.of(workerReports);
        Double m = Operations.median(s);
        assert Double.compare(m, 1.5) == 0 : m;
    }

    static void testCount() {
        Stream<WorkerReport> s = Stream.of(workerReports);
        Long c = Operations.count(s);
        assert Long.compare(c, 6) == 0 : c;
    }

    static void testStandardDeviation() {
        Stream<WorkerReport> s = Stream.of(workerReports);
        Double x = Operations.standardDeviation(s);
        assert Math.abs(x - 1.471960144388) < Math.pow(10, -5) : x;
    }
}
