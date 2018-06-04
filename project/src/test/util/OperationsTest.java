package util;

import org.junit.jupiter.api.Test;
import report.WorkerReport;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class OperationsTest {

    private static final double EPSILON = Math.pow(10, -6);

    private static WorkerReport[] workerReports = {
            new WorkerReport(0),
            new WorkerReport(1),
            new WorkerReport(1),
            new WorkerReport(2),
            new WorkerReport(3),
            new WorkerReport(4),
    };

    @Test
    void count() {
        Stream<WorkerReport> s = Stream.of(workerReports);
        assertEquals((long) Operations.count(s), (long) workerReports.length);
    }

    @Test
    void sum() {
        Stream<WorkerReport> s = Stream.of(workerReports);
        long n = Operations.sum(s);
        assertEquals(n, 11L);
    }

    @Test
    void max() {
        Stream<WorkerReport> s = Stream.of(workerReports);
        int n = Operations.max(s);
        assertEquals(n, 4);
    }

    @Test
    void min() {
        Stream<WorkerReport> s = Stream.of(workerReports);
        int n = Operations.min(s);
        assertEquals(n, 0);
    }

    @Test
    void average() {
        Stream<WorkerReport> s = Stream.of(workerReports);
        double x = Operations.average(s);
        assertEquals(x, 1.833333333, EPSILON);
    }

    @Test
    void median() {
        Stream<WorkerReport> s = Stream.of(workerReports);
        double m = Operations.median(s);
        assertEquals(m, 1.5, EPSILON);
    }

    @Test
    void standardDeviation() {
        Stream<WorkerReport> s = Stream.of(workerReports);
        double x = Operations.standardDeviation(s);
        assertEquals(x, 1.471960144388, EPSILON);
    }
}