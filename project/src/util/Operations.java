package util;

import report.WorkerReport;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Operations {

    public static Long count(Stream<WorkerReport> s) {
        return s.count();
    }

    public static Long sum(Stream<WorkerReport> s) {
        return s.mapToLong(WorkerReport::getLongValue).sum();
    }

    public static Integer max(Stream<WorkerReport> s) {
        return s.mapToInt(WorkerReport::getValue).max().orElse(Integer.MIN_VALUE);
    }

    public static Integer min(Stream<WorkerReport> s) {
        return s.mapToInt(WorkerReport::getValue).min().orElse(Integer.MAX_VALUE);
    }

    public static Double average(Stream<WorkerReport> s) {
        return s.mapToInt(WorkerReport::getValue).average().orElse(0);
    }

    public static Double median(Stream<WorkerReport> s) {
        IntStream sortedValues = s.mapToInt(WorkerReport::getValue).sorted();
        Long size = s.count();
        return size % 2 == 0 ?
                sortedValues.skip(size / 2 - 1).limit(2).average().orElse(0) :
                sortedValues.skip(size / 2).limit(1).average().orElse(0);
    }

    /**
     * Sample Standard Deviation of objects in the provided stream.
     *
     * @param s the Stream of WorkerReport objects.
     * @return the sample standard deviation of the given stream of objects.
     * @see <a href="https://en.wikipedia.org/wiki/Standard_deviation">Standard Deviation</a>
     */
    public static Double standardDeviation(Stream<WorkerReport> s) {
        Double avg = average(s);
        Double squaredDiff = s.mapToInt(WorkerReport::getValue)
                .mapToDouble((int i) -> Math.pow(i - avg, 2))
                .sum();

        return Math.sqrt(squaredDiff / (count(s) - 1));
    }

}
