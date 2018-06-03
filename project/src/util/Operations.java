package util;

import report.WorkerReport;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

public class Operations {

    public static Long count(Stream<WorkerReport> s) {
        return s.mapToLong((e) -> 1L).sum();
    }

    public static Integer max(Stream<WorkerReport> s) {
        Optional<WorkerReport> max = s.max(WorkerReport::compareTo);
        return max.isPresent() ? max.get().getValue() : Integer.MIN_VALUE;
    }

    public static Integer min(Stream<WorkerReport> s) {
        Optional<WorkerReport> min = s.min(WorkerReport::compareTo);
        return min.isPresent() ? min.get().getValue() : Integer.MAX_VALUE;
    }

    // TODO
    public static Integer median(Stream<WorkerReport> s) {
        return null;
    }

    // TODO
    public static Integer standardDeviation(Stream<WorkerReport> s) {
        return null;
    }

}
